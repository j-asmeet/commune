import { Button, CircularProgress, Flex, Text } from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { NavLink } from 'react-router-dom';


function CommunityDetails(props) {

    let { cid } = props;
    const [loading, setLoading] = useState(true);
    const [communityDetails, setCommunityDetails] = useState();
    const [interests, setInterests] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const getOptions = {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('BearerToken')
                }
            }
            try {
                setLoading(true);
                const response = await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}`, getOptions);

                const interestResponse = await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}/interest`, getOptions);

                if (response.ok && interestResponse.ok) {
                    const responseData = await response.json();
                    const interestResponseData = await interestResponse.json();
                    setCommunityDetails(responseData);
                    setInterests(interestResponseData);
                    setLoading(false);
                }
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [cid]);



    return (
        loading ? <Flex w="100%" minHeight="90vh" flexDirection="column" alignItems="center" justifyContent="center">
            <CircularProgress isIndeterminate color="teal" />
        </Flex> :
            <Flex flexDirection="column" w="100%" mt="24px" border="2px" borderColor="black" borderRadius="5px" alignItems="start" p="16px">
                <Text fontSize="3xl" fontWeight="medium" mt="16px">Welcome to {communityDetails.name}</Text>
                        <Text mt="16px">Community Description:</Text>
                        <Text fontSize="xl" fontWeight="medium">{communityDetails.description}</Text>
                        <Text mt="16px">Community Interests: </Text>
                        <Flex wrap="wrap" w="300px" gap="16px">
                            {interests.map((item, key) => (
                                <Text fontWeight="medium" key={key}>{item.interestName}</Text>
                            ))}
                        </Flex>
                <NavLink to={"/community/" + cid}>
                    <Button colorScheme='teal'>Visit</Button>
                </NavLink>
            </Flex>
    );
}

export default CommunityDetails;