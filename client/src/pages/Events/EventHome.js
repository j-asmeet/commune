import { Box, Button, Card, CardBody, CardHeader, Flex, Heading, Stack, StackDivider, Text ,ListItem, UnorderedList,} from "@chakra-ui/react";
import { React, useEffect, useState } from "react";
import { useNavigate, useParams } from 'react-router-dom';
import styles from './event.module.css';

const EventHome = () => {
    // const userId =2;
    let { eid } = useParams();
    const userId = localStorage.getItem('userID');
    const navigate = useNavigate();

    // const [loading, setLoading] = useState(true);
    const [eventDetails, setEventDetails] = useState();
    const [eventInterests, setEventInterests] = useState([]);
    const [loading, setLoading] = useState(true);
    // const userid = localStorage.getItem('userID');
    const handleEditClick = () => {
        console.log("event details: " + eventDetails);
        console.log("event interests: " + eventInterests);
        navigate(`/Events/${eventDetails.eventId}/edit-event`, { state: { eventDetails, eventInterests } });
    }

    const handleDeleteClick = async (eventId) => {
        try {
            const response = await fetch(`https://commune-dev-csci5308-server.onrender.com/events/${eid}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('BearerToken')
                }
            });
    
            if (response.ok) {
                navigate("/dashboard");
                console.log("Event deleted");
            } else {
                console.error(`Error deleting event: ${response.statusText}`);
            }
        } catch (error) {
            console.error(`Error deleting event: ${error}`);
        }
    }

    const handleUserRegistration = async (eventId, userId) => {
        try {
            console.log("Event id: " + eventId);

            const response = await fetch(`https://commune-dev-csci5308-server.onrender.com/events/${eventId}/users`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('BearerToken')
                },
                body: JSON.stringify({
                    eventId: eventId,
                    userId: userId
                })
            });
            if (response.ok) {
                console.log(`User ${userId} registered successfully for event ${eventId}`);
                navigate("/event-list");
            } else {
                console.error(`Error registering user for event: ${response.statusText}`);
            }
        } catch (error) {
            console.error(`Error registering user for event: ${error}`);
        }
    }
    

    useEffect(() => {
        const getEventInformation = async () => {
            const getOptions = {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('BearerToken')
                }
            }
            try {
                // console.log("Event id: "+ eid);
                setLoading(true);
                const response = await fetch(`https://commune-dev-csci5308-server.onrender.com/events/${eid}`, getOptions);
                if (response.ok) {
                    // console.log("response ok");
                    const responseData = await response.json();
                    setEventDetails(responseData);
                }

                const interestResponse = await fetch(`https://commune-dev-csci5308-server.onrender.com/events/${eid}/interests`, getOptions);
                if (interestResponse.ok) {
                    const interestResponseData = await interestResponse.json();
                    setEventInterests(interestResponseData);
                    setLoading(false);

                }
            } catch (error) {
                console.error(error);
            }
        }
        getEventInformation();
    }
        , [eid]);

    return (
        <>
            {!loading &&
            <div style={{ textAlign: 'left' }}>
                <div className={styles.homeContainer}>
                    <Card alignContent={"left"}>
                        <CardHeader>
                            <Heading size='lg'>Event Information</Heading>
                        </CardHeader>

                        <CardBody>
                            <Stack divider={<StackDivider />} spacing='4' alignItems="left">
                                <Box>
                                    <Heading size='md' textTransform='uppercase'>
                                        Event Name:
                                    </Heading>
                                    <Text pt='2' fontSize='md'>
                                        {eventDetails.eventName}
                                    </Text>
                                </Box>
                                <Box>
                                    <Heading size='md' textTransform='uppercase'>
                                        Description:
                                    </Heading>
                                    <Text pt='2' fontSize='md'>
                                        {eventDetails.shortDescription}
                                    </Text>
                                </Box>
                                <Box>
                                    <Heading size='md' textTransform='uppercase'>
                                        More Information:
                                    </Heading>
                                    <Text pt='2' fontSize='md'>
                                        {eventDetails.description}
                                    </Text>
                                </Box>
                                <Box>
                                    <Heading size='md' textTransform='uppercase'>
                                        Location:
                                    </Heading>
                                    <Text pt='2' fontSize='md'>
                                        {eventDetails.location}
                                    </Text>
                                </Box>
                                <Box>
                                    <Heading size='md' textTransform='uppercase'>
                                        Start Time:
                                    </Heading>
                                    <Text pt='2' fontSize='md'>
                                        {eventDetails.eventStartTime}
                                    </Text>
                                </Box>
                                <Box>
                                    <Heading size='md' textTransform='uppercase'>
                                        End Time:
                                    </Heading>
                                    <Text pt='2' fontSize='md'>
                                        {eventDetails.eventEndTime}
                                    </Text>
                                </Box>
                                <Box>
                                    <Heading size='md' textTransform='uppercase'>
                                        Event Type:
                                    </Heading>
                                    <Text pt='2' fontSize='md'>
                                        {eventDetails.eventType}
                                    </Text>
                                </Box>
                                <Box justifyContent="center">
                                    <Heading size='md' textTransform='uppercase'>
                                        Event Category:
                                    </Heading>
                                    <Flex w="340px" justifyContent="left" wrap="wrap" gap="8px">
                                        <UnorderedList>
                                        {eventInterests.map((interest) => {
                                            return (
                                                <ListItem key={interest.interestId}>
                                                    {interest.interestName}
                                                </ListItem>
                                            );
                                        }
                                        
                                        )}
                                        </UnorderedList>
                                    </Flex>
                                </Box>
                                {userId.toString() === eventDetails.createdByUserId.toString() &&
                                    <Box>
                                        <Button colorScheme='teal' variant='solid' marginRight="50px" onClick={() => handleEditClick(eventDetails, eventInterests)}>
                                            Edit Event
                                        </Button>

                                        <Button colorScheme='red' variant='solid' onClick={() => handleDeleteClick(eid)}>
                                            Delete Event
                                        </Button>
                                    </Box>
                                }
                                {
                                    userId.toString() !== eventDetails.createdByUserId.toString() &&
                                    <Box>
                                        <Button colorScheme='teal' variant='solid' marginRight="50px" onClick={() => handleUserRegistration(eid, userId)}>
                                            Register for Event
                                        </Button>
                                    </Box>
                                }
                            </Stack>
                        </CardBody>
                    </Card>
                </div>
                </div>
                }
        </>
    );
}

export default EventHome;