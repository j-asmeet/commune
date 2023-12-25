import { Box, Button, Flex, Heading,  Text } from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { NavLink, useParams } from 'react-router-dom';
import CommunityMembers from '../../components/SideBar/CommunityMembers';
import CommunitySideBar from '../../components/SideBar/CommunitySideBar';

function CommunityPosts() {
    const choice = 1;
    let { cid } = useParams();
    // const [communityDetails, setCommunityDetails] = useState();
    const [loading, setLoading] = useState(true);
    const [postData, setPostData] = useState([]);



    useEffect(() => {
        const fetchData = async () => {
            try {
                const requestOptions = {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': localStorage.getItem('BearerToken')
                    }
                };

                const response = await fetch(`https://commune-dev-csci5308-server.onrender.com/posts/community/${cid}`, requestOptions);
                // const response = await fetch(`https://commune-dev-csci5308-server.onrender.com/posts/`, requestOptions);

                const data = await response.json();
                // setCommunityDetails(responseData);
                setPostData(data);

                setLoading(false);

            } catch (error) {
                console.error("Error fetching posts:", error);
                setLoading(false);
            }
        };

        fetchData();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }
    const PostCard = ({ title, description }) => (
        <Box p={4} borderWidth="1px" borderRadius="lg" boxShadow="md" marginLeft={"15px"}>
            <Heading as="h3" size="md" mb={2}>
                {title}
            </Heading>
            <Text>{description}</Text>
        </Box>

    );

    console.log(postData);

    return (
        <Flex minH="90vh">
            <Flex w="15%" justifyContent="center" alignItems="center" h="90vh">
                <CommunitySideBar selectedTab={choice} />

            </Flex>
            <Flex w="65%">
                <Flex w="100%" flexDirection="column" ml="64px" mr="64px" mt="24px" gap="16px">
                    <NavLink to={"/community/" + cid + "/create-post"}>
                        <Button colorScheme='teal'>
                            Create Post
                        </Button>
                    </NavLink>
                    {postData.map((post) => (
                        <PostCard
                            key={post.postId}
                            title={post.postTitle}
                            description={post.description}
                        />
                    ))}
                </Flex>
            </Flex>


            <Flex w="20%">
                <CommunityMembers />
            </Flex>
        </Flex>
    );
}

export default CommunityPosts;