import { Box, Button, FormControl, FormLabel, Heading, Input, Select, Textarea, CircularProgress, Flex } from "@chakra-ui/react";
import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import "../Community/CommunityStyles.css";

const EditCommunity = () => {

    let { cid } = useParams();
    const [communityDetails, setCommunityDetails] = useState(null);
    const [description, setDescription] = useState(communityDetails?.description ?? '');
    const [name, setName] = useState(communityDetails?.name ?? '');
    const [interests, setInterests] = useState([]);
    const [interestList, setInterestList] = useState([]);
    const [loading, setLoading] = useState("false");
    const [initInterest, setInitInterest] = useState([]);
    const userid = localStorage.getItem("userID");
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const getOptions = {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': localStorage.getItem('BearerToken')
                    }
                }
                setLoading("true");
                const response = await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}`, getOptions);
                const interestResponse = await fetch('https://commune-dev-csci5308-server.onrender.com/interest', getOptions);

                const cinterestResponse = await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}/interest`, getOptions);

                if (response.ok && interestResponse.ok && cinterestResponse.ok) {
                    const responseData = await response.json();
                    const interestResponseData = await interestResponse.json();
                    const cinterestResponseData = await cinterestResponse.json();

                    setInterests(cinterestResponseData);

                    setCommunityDetails(responseData);
                    setInterestList(interestResponseData);
                    setInitInterest(cinterestResponseData);
                    setName(communityDetails?.name);
                    setDescription(communityDetails?.description);
                    setLoading("false");
                }
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const handleNameChange = (event) => {
        setName(event.target.value);
    };

    const handleDescriptionChange = (event) => {
        setDescription(event.target.value);
    };

    const handleInterestChange = (event) => {
        const selectedInterests = Array.from(event.target.options)
            .filter((option) => option.selected)
            .map((option) => option.value);
        setInterests(selectedInterests);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        // Perform the create community logic here
        console.log('Name:', name);
        console.log('Description:', description);
        console.log('Interests:', interests);

        try {

            const requestOptions = {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('BearerToken')
                },
                body: JSON.stringify({ community_id: cid, created_by: userid, name: name, description: description, display_image: "link" })
            };

            const response = await fetch('https://commune-dev-csci5308-server.onrender.com/community', requestOptions);

            const postInterestOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('BearerToken')
                }
            }

            const deleteInterestOptions = {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('BearerToken')
                }
            }

            initInterest.forEach(async function (interest) {
                await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}/interest?interest_id=${interest.interestId}`, deleteInterestOptions);
            })


            interestList.forEach(async function (interest) {
                if (interests.includes(interest.interestName)) {
                    await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}/interest?interest_id=${interest.interestId}`, postInterestOptions);
                }
            })

            if (response.ok) {
                navigate(`/community/${cid}/admin`);
            } else {

            }

        } catch (error) {
            console.error(error);
        }

        // Reset the form fields
        // setName('');
        // setDescription('');
        // setInterests([]);
    };

    return (
        communityDetails !== null ?
            <Box className="main-div">
                <Box className="container-div">
                    <Heading>Edit community information</Heading>
                    <form onSubmit={handleSubmit}>
                        <FormControl id="name" marginTop="16px">
                            <FormLabel>Name</FormLabel>
                            <Input
                                type="text"
                                placeholder={name}
                                value={name}
                                onChange={handleNameChange}
                            />
                        </FormControl>

                        <FormControl id="description" marginTop="16px">
                            <FormLabel>Description</FormLabel>
                            <Textarea
                                rows={3}
                                placeholder={description}
                                value={description}
                                onChange={handleDescriptionChange}
                            />
                        </FormControl>

                        <FormControl id="interests" marginTop="16px">
                            <FormLabel>Interests</FormLabel>
                            <Select multiple onChange={handleInterestChange} h="30vh">
                                {loading === "true" ? <option>Loading...</option> : interestList.map((item, key) => (
                                    <option value={item.interestName} key={key}>
                                        {item.interestName}
                                    </option>
                                ))}
                            </Select>
                        </FormControl>

                        <Button colorScheme="teal" type="submit" marginTop="32px">
                            Edit
                        </Button>
                    </form>
                </Box>
            </Box> : <Flex w="100%" minHeight="90vh" flexDirection="column" alignItems="center" justifyContent="center">
                <CircularProgress isIndeterminate color="teal" />
            </Flex>
    );
};

export default EditCommunity;