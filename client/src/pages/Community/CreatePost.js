import { Box, Button, FormControl, FormLabel, Heading, Input, Textarea } from "@chakra-ui/react";
import React, { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';


const CreatePost = () => {
    const [postTitle, setPostTitle] = useState("");
    const [postDescription, setPostDescription] = useState("");
    //   const [postImage, setPostImage] = useState(null);
    // const [loading, setLoading] = useState(false);
    const userid = localStorage.getItem('userID');
    const navigate = useNavigate();
    let { cid } = useParams();


    // useEffect(() => {
    //     const fetchData = async () => {
    //         const getOptions = {
    //             method: 'GET',
    //             headers: {
    //                 'Content-Type': 'application/json',
    //                 'Authorization': localStorage.getItem('BearerToken')
    //                 // 'Authorization' : 'eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiamFzbWVldHNpbmdoMjM1OUBnbWFpbC5jb20iLCJpYXQiOjE2OTA0OTEwOTIsInVzZXJJZCI6NTE3MDgxNDkwfQ.AjTZPA9meDd9maMha1grnkaoxSjut-VFSBdGqVE8gVJRiS-RM5BMjG_EUzIZmZvBZK6LZhPiQMiyhPHWh3eewDD7_1NrDiJIKvZH_CMHcIsE_whWjzTGSQc8ZdfERSTOBl_F2h3N-u7lxg1QgLkZbZKf4rDPcAVtfoSbCCjj2-pqRDcOrbQYji4RAkiozkFnvL6wGoOp1ZdY137tcACQAejjJLCuyMC2FeBE_9tbx6XUXdOCO61x1b_N2R9O55OMxfIiZ6HE1cK2Z3YWmj8yPxyoBrks-JvDfeKcbdnqfVWtgYFGyvb5E0z2KjAG4uwS7cln3bNckrJk9Q8GwqiaIA'
    //             }
    //         }

    //         // try {
    //         //     setLoading(true);
    //         //     console.log('reached');
    //         //     const response = await fetch('https://commune-dev-csci5308-server.onrender.com/posts/', getOptions);
    //         //     console.log('response: ', response);
    //         //     if (response.ok) {
    //         //         const responseData = await response.json();
    //         //         console.log(responseData);
    //         //         // setPostList(responseData);
    //         //         setLoading(false);
    //         //     }
    //         // } catch (error) {
    //         //     console.error(error);
    //         // }
    //     };

    //     fetchData();
    // }, []);

    const handleTitleChange = (post) => {
        setPostTitle(post.target.value);
    };

    const handleDescriptionChange = (post) => {
        setPostDescription(post.target.value);
    };

    const handleSubmit = async (post) => {
        console.log('here');
        post.preventDefault();
        // Perform the create community logic here
        console.log('Title:', postTitle);
        console.log('Description:', postDescription);
        // console.log('Image: ', postImage);

        try {

            const requestOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('BearerToken')
                },
                body: JSON.stringify({
                    postTitle: postTitle,
                    description: postDescription,
                    postImage: 'postImage',
                    userId: userid,
                    communityId: cid
                })
            };

            const response = await fetch('https://commune-dev-csci5308-server.onrender.com/posts', requestOptions);

            // const communityID = await response.text();
            // console.log("community id: " + communityID);


            if (response.ok) {
                navigate(`/community/${cid}/posts`);
            } else {
                // console.log(loading);
            }

        } catch (error) {
            console.error(error);
        }
    };

    return (
        <Box className="main-div">
            <Box className="container-div">
                <Heading>Create a new post</Heading>
                <div style={{ width: "90%" }}>
                    <FormControl>
                        <form onSubmit={handleSubmit}>
                            <FormControl mt={4}>
                                <FormLabel>Post Title</FormLabel>
                                <Input
                                    type="text"
                                    value={postTitle}
                                    onChange={handleTitleChange}
                                />
                            </FormControl>

                            <FormControl mt={4}>
                                <FormLabel>Post Description</FormLabel>
                                <Textarea
                                    value={postDescription}
                                    onChange={handleDescriptionChange}
                                />
                            </FormControl>

                            {/* <FormControl mt={4}>
                                <FormLabel>Post Image</FormLabel>
                                    <Input type="file" value = {postImage}/>
                                </FormControl> */}

                        </form>
                    </FormControl>
                </div>
                <Button variant="solid" type="submit" marginTop="32px" onClick={handleSubmit} >
                    Create
                </Button>

            </Box>
        </Box>
    );
};

export default CreatePost;