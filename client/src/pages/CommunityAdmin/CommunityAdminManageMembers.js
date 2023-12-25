import { Button, CircularProgress, Flex, Text } from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import CommunityAdminSideBar from '../../components/SideBar/CommunityAdminSideBar';

function CommunityAdminManageMembers() {
    const choice = 1;
    let { cid } = useParams();
    const [communityDetails, setCommunityDetails] = useState();
    const [loading, setLoading] = useState(true);
    const [members, setMembers] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const getOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('BearerToken')
            }
        }
        const fetchData = async () => {
            try {
                setLoading(true);
                const response = await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}`, getOptions);
                const memberResponse = await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}/members`, getOptions);

                if (response.ok && memberResponse.ok) {
                    const responseData = await response.json();
                    const memberResponseData = await memberResponse.json();
                    setCommunityDetails(responseData);
                    setMembers(memberResponseData);
                    setLoading(false);
                }
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [cid]);

    const deleteMember = async (event) => {

        const userid = event.target.value;
        const deleteMemberOptions = {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('BearerToken')
            },
            body: JSON.stringify({ community_id: cid, user_id: userid, user_role: "Member" })
        }

        await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}/members`, deleteMemberOptions);

        navigate(`/community/${cid}/admin/`);

    };

    const updateMember = async (event) => {

        const userid = event.target.value;
        const updateMemberOptions = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('BearerToken')
            },
            body: JSON.stringify({ community_id: cid, user_id: userid, user_role: "Admin" })
        }

        await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${cid}/members?new_role=Admin`, updateMemberOptions);

        navigate(`/community/${cid}/admin/`);

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
                    <Flex w="100%" flexDirection="column" justifyContent="start" alignItems="start" ml="64px">
                        <Text fontSize="xl" fontWeight="medium" mt="24px">Members of {communityDetails.name}</Text>
                        {
                            loading ? <Flex w="100%" minHeight="90vh" flexDirection="column" alignItems="center" justifyContent="center">
                                <CircularProgress isIndeterminate color="teal" />
                            </Flex> :
                                members.map((item, key) => (
                                    <Flex w="69%" gap="8px" justifyContent="space-between" mt="40px" border="2px" borderColor="black" borderRadius="10px" p="16px">
                                        <Flex flexDirection="column">
                                            <Text fontWeight="medium">Member name: {item.user_name}</Text>
                                            <Text fontWeight="medium">Member role: {item.user_role}</Text>
                                        </Flex>
                                        <Flex flexDirection="column" gap="8px" justifyContent="center">
                                            {item.user_role === "Admin" ? null : <Button colorScheme='teal' onClick={updateMember} value={item.user_id}>Promote</Button>}
                                            <Button colorScheme='teal' onClick={deleteMember} value={item.user_id}>Remove</Button>
                                        </Flex>
                                    </Flex>
                                ))
                        }
                    </Flex>
                }

            </Flex>
        </Flex>
    );
}

export default CommunityAdminManageMembers;