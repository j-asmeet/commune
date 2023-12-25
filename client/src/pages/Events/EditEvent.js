import React, { useEffect, useState } from 'react';
import { Grid, GridItem, Box, Button, FormControl, FormLabel, Input, Select, Textarea, Flex, Center } from "@chakra-ui/react";
import { useLocation, useNavigate } from 'react-router-dom';
import styles from './edit.module.css';

const EditEvent = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const eventDetails = location.state.eventDetails;
    const eventInterests = location.state.eventInterests;

    const eventStartTime = new Date(eventDetails?.eventStartTime).toISOString().slice(0, 16);
    const eventEndTime = new Date(eventDetails?.eventEndTime).toISOString().slice(0, 16);
    const interests = eventInterests?.map(interest => interest.interestName.toString()) || [];

    const [name, setName] = useState(eventDetails?.eventName || '');
    const [shortDescription, setShortDescription] = useState(eventDetails?.shortDescription || '');
    const [description, setDescription] = useState(eventDetails?.description || '');
    const [selectedInterests, setSelectedInterests] = useState(interests);
    const [interestList, setInterestList] = useState([]);
    const [eventType, setEventType] = useState(eventDetails?.eventType || '');
    const [address, setAddress] = useState(eventDetails?.location.split(",")[1].trim() || '');
    const [venue, setVenue] = useState(eventDetails?.location.split(",")[0].trim() || '');
    const [startTime, setStartTime] = useState(eventStartTime);
    const [endTime, setEndTime] = useState(eventEndTime);
    const [loading, setLoading] = useState(false);
    const userId = localStorage.getItem('userID');

    const handleSubmit = async (event) => {
        event.preventDefault();
        const date = new Date(startTime);
        const formattedStartTime = date.toISOString();
        const date1 = new Date(endTime);
        const formattedEndTime = date1.toISOString();
        // console.log(name);
        // console.log(shortDescription);
        // console.log(description);
        // console.log(selectedInterests);
        // console.log(eventType);
        // console.log(address);
        // console.log(venue);
        // console.log(formattedStartTime);
        // console.log(formattedEndTime);
        // console.log(userId);
        // console.log(eventDetails.eventId);

        try {
            const eventLocation = venue + " , " + address;
            const requestOptions = {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('BearerToken')
                },
                body: JSON.stringify({
                    eventId: eventDetails.eventId,
                    eventName: name,
                    shortDescription: shortDescription,
                    description: description,
                    location: eventLocation,
                    eventStartTime: formattedStartTime,
                    eventEndTime: formattedEndTime,
                    eventPoster: "empty",
                    entryFees: 100,
                    eventType: eventType,
                    createdByUserId: userId
                })
            };

            const response = await fetch(`https://commune-dev-csci5308-server.onrender.com/events`, requestOptions);
            console.log(response);
            const deleteInterestOptions = {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('BearerToken')
                }
            }
            const eventId = eventDetails.eventId;

            await Promise.all(eventInterests.map(async (interest) => {
                await fetch(`https://commune-dev-csci5308-server.onrender.com/events/${eventId}/interests?interest_id=${interest.interestId}`, deleteInterestOptions);
            }));


            const postInterestOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('BearerToken')
                }
            }
            console.log("Interests: " + interests);
            console.log("selected interests: " + selectedInterests);
            console.log(postInterestOptions);

            await Promise.all(interestList.map(async (interest) => {
                if (selectedInterests.includes(interest.interestName)) {
                    await fetch(`https://commune-dev-csci5308-server.onrender.com/events/${eventId}/interests?interest_id=${interest.interestId}`, postInterestOptions);
                }
            }));
            navigate(`/events/${eventDetails.eventId}`);
        } catch (error) {
            console.error(error);
        }
    }


    const handleNameChange = (event) => {
        setName(event.target.value);
    }

    const handleShortDescriptionChange = (event) => {
        setShortDescription(event.target.value);
    }

    const handleDescriptionChange = (event) => {
        setDescription(event.target.value);
    }

    const handleInterestChange = (event) => {
        const selectedInterests = Array.from(event.target.options)
            .filter((option) => option.selected)
            .map((option) => option.value);
        setSelectedInterests(selectedInterests);
    };

    const handleEventTypeChange = (event) => {
        setEventType(event.target.value);
    }

    const handleAddressChange = (event) => {
        setAddress(event.target.value);
    }

    const handleVenueChange = (event) => {
        setVenue(event.target.value);
    }

    const handleStartTimeChange = (event) => {
        setStartTime(event.target.value);
    }

    const handleEndTimeChange = (event) => {
        setEndTime(event.target.value);
    }

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
                const response = await fetch('https://commune-dev-csci5308-server.onrender.com/interest', getOptions);
                if (response.ok) {
                    const responseData = await response.json();
                    setInterestList(responseData);
                    setLoading(false);
                }
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
    }, []);

    return (
        <Box className="main-div">
            <Box className="container-div" h="100vh">
            <Flex direction="column" align="start" className={styles.container}>
                <Center className={styles.title}>
                    <h1>Edit an Event</h1>
                </Center>
                <Box className={styles.formContainer}>
                <form onSubmit={handleSubmit}>
                            <FormControl id="name" marginTop="16px">
                                <FormLabel>Event Name</FormLabel>
                                <Input
                                    type="text"
                                    placeholder="Enter Event name"
                                    value={name}
                                    onChange={handleNameChange}
                                />
                            </FormControl>
                            <Grid templateColumns='repeat(2, 1fr)' gap={6} margin="16px 0px 50px 0px">
                                <GridItem w='100%' h='10' >
                                    <FormControl id="city">
                                        <FormLabel>Event Address</FormLabel>
                                        <Input
                                            type="text"
                                            placeholder="Enter Event city"
                                            value={address}
                                            onChange={handleAddressChange}
                                        />
                                    </FormControl>
                                </GridItem>
                                <GridItem w='100%' h='10' >
                                    <FormControl id="venue">
                                        <FormLabel>Venue</FormLabel>
                                        <Input
                                            type="text"
                                            placeholder="Enter Event venue"
                                            value={venue}
                                            onChange={handleVenueChange}
                                        />
                                    </FormControl>
                                </GridItem>
                            </Grid>
                            <Grid templateColumns='repeat(2, 1fr)' gap={6} margin="16px 0px 50px 0px">
                                <GridItem w='100%' h='10' >
                                    <FormControl id="startTime">
                                        <FormLabel>Start time:</FormLabel>
                                        <Input
                                            placeholder="Select Date and Time"
                                            size="md"
                                            type="datetime-local"
                                            value={startTime}
                                            onChange={handleStartTimeChange}
                                        />
                                    </FormControl>
                                </GridItem>
                                <GridItem w='100%' h='10' >
                                    <FormControl id="endTime">
                                        <FormLabel>End time:</FormLabel>
                                        <Input
                                            placeholder="Select Date and Time"
                                            size="md"
                                            type="datetime-local"
                                            value={endTime}
                                            onChange={handleEndTimeChange}
                                        />
                                    </FormControl>
                                </GridItem>
                            </Grid>
                            <FormControl>
                                <FormLabel>Event Category</FormLabel>
                                <Select multiple value={selectedInterests} onChange={handleInterestChange} h="30vh">
                                    {loading ? <option>Loading...</option> : interestList.map((item, key) => (
                                        <option value={item.interestName} key={key}>
                                            {item.interestName}
                                        </option>
                                    ))}
                                </Select>
                            </FormControl>
                            <FormLabel marginTop="16px">Event Type</FormLabel>
                            <Select placeholder='Select Event Type' defaultValue={eventType} onChange={handleEventTypeChange}>
                                <option value='in-person'>In-person</option>
                                <option value='option2'>Online</option>
                            </Select>
                            <FormControl id="shortDescription" marginTop="16px">
                                <FormLabel>Short Description</FormLabel>
                                <Input
                                    type="text"
                                    placeholder="Enter Event short description"
                                    value={shortDescription}
                                    onChange={handleShortDescriptionChange}
                                />
                            </FormControl>
                            <FormControl id="description" marginTop="16px">
                                <FormLabel>Event Description</FormLabel>
                                <Textarea
                                    rows={5}
                                    placeholder="Enter event description"
                                    value={description}
                                    onChange={handleDescriptionChange}
                                />
                            </FormControl>
                            <Button onSubmit={handleSubmit} variant="solid" colorScheme='teal' size='md' type="submit">
                                Edit Event
                            </Button>
                            </form>
                        </Box>
                    </Flex>
                </Box>
            </Box>
        // <Box className="main-div">
        //     <Box className="container-div">
        //         <div className={styles.container}>
        //             <div className={styles.title}>
        //                 <h1>Edit an Event</h1>
        //             </div>
        //             <form onSubmit={handleSubmit} className={styles.form}>
        //                 <Grid templateColumns="repeat(2, 1fr)" gap={6}>
        //                     <GridItem colSpan={2} >
        //                         <FormControl>
        //                             <FormLabel>Event Name</FormLabel>
        //                             <Input type="text" onChange={handleNameChange} value={name} required/>
        //                         </FormControl>
        //                         <FormControl>
        //                             <FormLabel>Short Description</FormLabel>
        //                             <Input type="text" onChange={handleShortDescriptionChange} value={shortDescription} required/>
        //                         </FormControl>
        //                         <FormControl>
        //                             <FormLabel>Description</FormLabel>
        //                             <Textarea placeholder="Here is a sample placeholder" size="sm" onChange={handleDescriptionChange} value={description} required/>
        //                         </FormControl>
        //                         <FormControl>
        //                             <FormLabel>Interests</FormLabel>
        //                             <Select placeholder="Select Interests" multiple onChange={handleInterestChange} value={selectedInterests} required>
        //                                 {interestList.map((interest, index) => (
        //                                     <option key={index} value={interest.interestName}>
        //                                         {interest.interestName}
        //                                     </option>
        //                                 ))}
        //                             </Select>
        //                         </FormControl>
        //                         <FormControl>
        //                             <FormLabel>Event Type</FormLabel>
        //                             <Select placeholder="Select Event Type" onChange={handleEventTypeChange} value={eventType} required>
        //                                 <option value="Online">Online</option>
        //                                 <option value="Offline">Offline</option>
        //                             </Select>
        //                         </FormControl>
        //                         <FormControl>
        //                             <FormLabel>Address</FormLabel>
        //                             <Input type="text" onChange={handleAddressChange} value={address} required/>
        //                         </FormControl>
        //                         <FormControl>
        //                             <FormLabel>Venue</FormLabel>
        //                             <Input type="text" onChange={handleVenueChange} value={venue} required/>
        //                         </FormControl>
        //                         <FormControl>
        //                             <FormLabel>Event Start Time</FormLabel>
        //                             <Input type="datetime-local" onChange={handleStartTimeChange} value={startTime} required/>
        //                         </FormControl>
        //                         <FormControl>
        //                             <FormLabel>Event End Time</FormLabel>
        //                             <Input type="datetime-local" onChange={handleEndTimeChange} value={endTime} required/>
        //                         </FormControl>
        //                     </GridItem>
        //                     <GridItem colSpan={2}>
        //                         <Button colorScheme="teal" size="md" type="submit" disabled={loading}>
        //                             Edit Event
        //                         </Button>
        //                     </GridItem>
        //                 </Grid>
        //             </form>
        //         </div>
        //     </Box>
        // </Box>
    );
}

export default EditEvent;
