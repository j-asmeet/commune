import { Button, CircularProgress, Flex, Text } from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { NavLink, useParams } from 'react-router-dom';
import CommunityMembers from '../../components/SideBar/CommunityMembers';
import CommunitySideBar from '../../components/SideBar/CommunitySideBar';

function CommunityHome() {
    const choice = 0;
    let { cid } = useParams();
    const [communityDetails, setCommunityDetails] = useState();
    const [loading, setLoading] = useState(true);
    const [interests, setInterests] = useState([]);
    const [members, setMembers] = useState([]);
    const [isMember, setIsMember] = useState("true");
    const [isAdmin, setIsAdmin] = useState("true");
    const userid = localStorage.getItem("userID");


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

                setLoading(true);
                const response = await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}`, getOptions);

                const interestResponse = await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}/interest`, getOptions);

                const memberResponse = await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}/members`, getOptions);

                if (response.ok && interestResponse.ok && memberResponse.ok) {
                    const responseData = await response.json();
                    const interestResponseData = await interestResponse.json();
                    const memberResponseData = await memberResponse.json();

                    setMembers(memberResponseData);
                    setCommunityDetails(responseData);
                    setInterests(interestResponseData);

                    members.forEach(function (member) {
                        if (member.user_id.toString() === userid.toString()) {
                            
                            setIsMember("true");
                            if (member.user_role === "Admin") {
                                setIsAdmin("true");
                            }
                        }
                    });

                    setLoading(false);

                    
                }
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [cid]);

    const handleMemberChange = async () => {
        const postMemberOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('BearerToken')
            },
            body: JSON.stringify({ community_id: cid, user_id: userid, user_role: "Member" })
        }

        const deleteMemberOptions = {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('BearerToken')
            },
            body: JSON.stringify({ community_id: cid, user_id: userid, user_role: "Member" })
        }

        isMember === "true" ?
            await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}/members`, deleteMemberOptions)
            :
            await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}/members`, postMemberOptions);

        setIsMember(!isMember);
    };

    return (
        <Flex minH="90vh">
            <Flex w="15%" alignItems="center" minH="90vh">
                <CommunitySideBar selectedTab={choice} />
            </Flex>
            <Flex w="65%">

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
                            {
                                isMember === "true" ?
                                    <Button colorScheme='teal' onClick={handleMemberChange}>Leave</Button> : <Button onClick={handleMemberChange}>Join</Button>
                            }
                            {
                                isAdmin === "true" ? <NavLink to={"/community/" + cid + "/admin"}><Button colorScheme="teal" ml="8px">Admin Page</Button></NavLink> : null
                            }
                        </Flex>
                    </Flex>
                }

            </Flex>
            <Flex w="20%">
                <CommunityMembers />
            </Flex>
        </Flex>
    );
}

export default CommunityHome;