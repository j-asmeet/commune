import { Button, CircularProgress, Flex, Text } from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import CommunityAdminSideBar from '../../components/SideBar/CommunityAdminSideBar';

function CommunityAdminSettings() {
    const choice = 2;
    let { cid } = useParams();
    const [communityDetails, setCommunityDetails] = useState();
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();


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


                if (response.ok) {
                    const responseData = await response.json();
                    setCommunityDetails(responseData);

                    setLoading(false);
                }
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [cid]);

    const handleDeleteCommunity = async () => {
        const RequestOptions = {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('BearerToken')
            }
        }

        await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}`, RequestOptions);

        navigate("/");
    };


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
                        <Text mt="140px" fontSize="3xl" fontWeight="medium">
                            Welcome to settings page of {communityDetails.name}
                        </Text>
                        <Text>
                            Are you sure you want to delete the community?
                        </Text>
                        <Button colorScheme="teal" onClick={handleDeleteCommunity}>Delete</Button>
                    </Flex>
                }

            </Flex>

        </Flex>
    );
}

export default CommunityAdminSettings;