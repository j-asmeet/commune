import { Button, FormControl, FormLabel, Input} from '@chakra-ui/react';
import React, { useState } from 'react';


function CreateComment() {
    // let { cid } = useParams();
    // const [communityDetails, setCommunityDetails] = useState();
    const [comment, setComment] = useState();
    // const[commentDate, setCommentDate] = useState(new Date().toISOString().slice(0, 10));
    // const [loading, setLoading] = useState(true);
    // const userid = 2;

    const handleSubmit = async (event) => {
        event.preventDefault();
    
        try {
          const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('BearerToken')
            },
            body: JSON.stringify({
                comment: "hiiii",
                comment_date: "2023-07-09 21:00:00",
                post_id: 3,
                userId: 1,
                communityId: 5
            })
        };

        const response = await fetch('https://commune-dev-csci5308-server.onrender.com/comments', requestOptions);
    
          if (response.ok) {
            console.log("Comment added successfully!");
          } else {
            console.error("Failed to add comment.");
          }
        } catch (error) {
          console.error("Error adding comment:", error);
        }
      };
    
    const handleCommentChange = (comment) => {
        setComment(comment.target.value);
    };
    
      return (
        <div>
        <form onSubmit={handleSubmit}>
           <FormControl mt={4}>
             <FormLabel>Post Title</FormLabel>
             <Input
                 type="text"
                 value={comment}
                 onChange= {handleCommentChange}
             />
             </FormControl>                   
          <Button type="submit">Submit Comment</Button>

       </form>
        </div>
    
      );
   
}

export default CreateComment;