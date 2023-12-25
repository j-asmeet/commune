import { Box, Button, FormControl, FormLabel, Heading, Input, Select, Textarea } from "@chakra-ui/react";
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import "./CommunityStyles.css";
import { getInterests, postCommunityInterest } from "../../services/InterestServices/InterestServices";
import { CreateNewCommunity } from "../../services/CommunityServices/CommunityServices";
import { postMember } from "../../services/MemberServices/MemberServices";

const CreateCommunity = () => {
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [interests, setInterests] = useState([]);
    const [interestList, setInterestList] = useState([]);
    const userid = localStorage.getItem('userID');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            const data = await getInterests();
            setInterestList(data);
        };
        fetchData();
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

        const communityID = await CreateNewCommunity(userid, name, description);

        await postMember(userid, communityID, "Admin");

        try{
            await Promise.all(interestList?.forEach(async function (interest) {
                if (interests.includes(interest.interestName)) {
                    await postCommunityInterest(communityID, interest.interestId);
                }
            }));
        }catch(error){

        }

        navigate(`/community/${communityID}`);
    };

    return (
        <Box className="main-div">
            <Box className="container-div">
                <Heading>Create a community</Heading>
                <form onSubmit={handleSubmit}>
                    <FormControl id="name" marginTop="16px">
                        <FormLabel>Name</FormLabel>
                        <Input
                            type="text"
                            placeholder="Enter community name"
                            value={name}
                            onChange={handleNameChange}
                        />
                    </FormControl>

                    <FormControl id="description" marginTop="16px">
                        <FormLabel>Description</FormLabel>
                        <Textarea
                            rows={3}
                            placeholder="Enter community description"
                            value={description}
                            onChange={handleDescriptionChange}
                        />
                    </FormControl>

                    <FormControl id="interests" marginTop="16px">
                        <FormLabel>Interests</FormLabel>
                        <Select multiple onChange={handleInterestChange} h="30vh">
                            {interestList.length === 0 ? <option>Loading...</option> : interestList.map((item, key) => (
                                <option value={item.interestName} key={key}>
                                    {item.interestName}
                                </option>
                            ))}
                        </Select>
                    </FormControl>

                    <Button colorScheme="teal" type="submit" marginTop="32px">
                        Create
                    </Button>
                </form>
            </Box>
        </Box>
    );
};

export default CreateCommunity;