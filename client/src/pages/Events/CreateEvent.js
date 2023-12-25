import React, { useEffect, useState } from 'react';
import { Grid, GridItem } from '@chakra-ui/react'
import { useNavigate } from 'react-router-dom';
import { Box, Button, FormControl, FormLabel, Input, Select, Textarea } from "@chakra-ui/react";
import styles from './event.module.css';

const CreateEvent = () => {
    const navigate = useNavigate();
    const [name, setName] = useState('');
    const [shortDescription, setShortDescription] = useState('');
    const [description, setDescription] = useState('');
    const [interests, setInterests] = useState([]);
    const [interestList, setInterestList] = useState([]);
    const [eventType, setEventType] = useState('');
    const [address, setAddress] = useState('');
    const [venue, setVenue] = useState('');
    const [startTime, setStartTime] = useState();
    const [endTime, setEndTime] = useState();
    const [loading, setLoading] = useState(false);
    // const [ userId, setUserid] = useState(2);
    const userId = localStorage.getItem('userID');

    const handleSubmit = async (event) => {
        event.preventDefault();
        const date = new Date(startTime);
        const formattedStartTime = date.toISOString();
        const date1 = new Date(endTime);
        const formattedEndTime = date1.toISOString();

        try {
            const eventLocation = venue + " , " + address;
            const requestOptions = {
                method: 'POST',
                headers: { 'Content-Type': 'application/json', 'Authorization': localStorage.getItem('BearerToken') },
                body: JSON.stringify({
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
            var eventId = 0;
            const response = await fetch('https://commune-dev-csci5308-server.onrender.com/events', requestOptions);
            if (response.ok) {
                const responseData = await response.json();
                eventId = responseData.eventId;
            }

            const postInterestOptions = {
                method: 'POST',
                headers: { 'Content-Type': 'application/json', 'Authorization': localStorage.getItem('BearerToken') }
            }

            interestList.forEach(async function (interest) {
                if (interests.includes(interest.interestName)) {
                    await fetch(`https://commune-dev-csci5308-server.onrender.com/events/${eventId}/interests?interest_id=${interest.interestId}`, postInterestOptions);
                }
            })

            if (response.ok) {
                navigate(`/events/${eventId}`);
            } else {

            }
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
        setInterests(selectedInterests);
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
            <Box className="container-div">
                <div className={styles.container}>
                    <div className={styles.title}>
                        <h1>Create an Event</h1>
                    </div>
                    <div className={styles.formContainer}>
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
                            <FormControl id="category" marginTop="16px">
                                <FormLabel>Event Category</FormLabel>
                                <Select multiple onChange={handleInterestChange} h="30vh">
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
                            <Button variant="solid" colorScheme='teal' size='md' type="submit" className={styles.submitButton}>
                                Create Event
                            </Button>
                        </form>
                    </div>
                </div>
            </Box>
        </Box>
    )
}

export default CreateEvent;