import React, { useEffect, useState } from 'react';
import { Flex, Text, Box, Button, Wrap, WrapItem } from '@chakra-ui/react';
import { NavLink } from 'react-router-dom';

function EventList() {
  const [eventList, setEventList] = useState([]);

  useEffect(() => {
    const getOptions = {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem('BearerToken')
      }
    };

    fetch('https://commune-dev-csci5308-server.onrender.com/events', getOptions)
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log(data);
        setEventList(data);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  }, []);

  return (
    <Box>
    <Text fontSize="xl" fontWeight="bold" my={4} position="sticky" top={0} zIndex="sticky" backgroundColor="white">
      All Events
    </Text>
    <Flex overflowX="auto" maxH="80vh" pb={4} overflow="auto">
      <Wrap justify="center" spacing={4} flexWrap="nowrap">
        {eventList.map(event => (
          <WrapItem key={event.eventId}>
            <Box p={4} borderWidth="1px" w="20vw" h="35vh" borderRadius="md">
              <Text fontWeight="bold">{event.eventName}</Text>
              <Text>{event.shortDescription}</Text>
              <Text mt={2} fontWeight="medium">
                {new Date(event.eventStartTime).toLocaleString()}
              </Text>
              <NavLink to={"/events/" + event.eventId}>
                          <Button mt={4} colorScheme="teal" variant="solid">
                            View Event
                          </Button>
                        </NavLink>
            </Box>
          </WrapItem>
        ))}
      </Wrap>
    </Flex>
  </Box>
  );
}

export default EventList;
