import { Button, Flex, CircularProgress, Text } from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { NavLink, useParams } from 'react-router-dom';
import CommunityAdminSideBar from '../../components/SideBar/CommunityAdminSideBar';

function CommunityAdminHome() {
    const choice = 0;
    let { cid } = useParams();
    const [communityDetails, setCommunityDetails] = useState();
    const [loading, setLoading] = useState(true);
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
        <Flex minH="90vh">
            <Flex w="15%" justifyContent="center" alignItems="center" h="90vh">
                <CommunityAdminSideBar selectedTab={choice} />
            </Flex>
            <Flex w="85%">

                {loading ? <Flex w="100%" minHeight="90vh" flexDirection="column" alignItems="center" justifyContent="center">
                    <CircularProgress isIndeterminate color="teal" />
                </Flex> :
                    <Flex flexDirection="column" justifyContent="start" alignItems="start" ml="64px">
                        <Text fontSize="3xl" fontWeight="medium" mt="16px">Welcome to {communityDetails.name}</Text>
                        <Text mt="16px">Community Description:</Text>
                        <Text fontSize="xl" fontWeight="medium">{communityDetails.description}</Text>
                        <Text mt="16px">Community Interests: </Text>
                        <Flex wrap="wrap" w="300px" gap="16px">
                            {interests.map((item, key) => (
                                <Text fontWeight="medium" key={key}>{item.interestName}</Text>
                            ))}
                        </Flex>
                        <Flex mt="16px">
                            <NavLink to={"/community/" + cid + "/admin/edit"}>
                                <Button colorScheme='teal'>Edit info</Button>
                            </NavLink>
                        </Flex>
                    </Flex>
                }

            </Flex>
        </Flex>
    );
}

export default CommunityAdminHome;