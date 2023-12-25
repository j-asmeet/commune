import { Button, CircularProgress, Flex } from "@chakra-ui/react";
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import CommunityMembers from "../../components/SideBar/CommunityMembers";
import CommunitySideBar from "../../components/SideBar/CommunitySideBar";
import {
    Box,
    Heading,
    Text,
    Badge,
    VStack,
    HStack,
    SimpleGrid,
} from '@chakra-ui/react';

function CommunityEvents() {
  const choice = 2;
  let { cid } = useParams();
  const [communityDetails, setCommunityDetails] = useState();
  const [loading, setLoading] = useState(true);
  const userid = localStorage.getItem("userID");
  const [events, setEvents] = useState([]);
  const navigate = useNavigate();

  const handleViewDetails = async (eventId) => {
    navigate(`/events/${eventId}`);
  }

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

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(
          `https://commune-dev-csci5308-server.onrender.com/events/${cid}/events`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: localStorage.getItem("BearerToken"),
            },
          }
        );

        if (response.ok) {
          const data = await response.json();
          setEvents(data);
          setLoading(false);
          console.log(data);
        } else {
          console.error(`Error fetching events: ${response.statusText}`);
        }
      } catch (error) {
        console.error(`Error fetching events: ${error}`);
      }
    };

    fetchData();
  }, [cid]);

  return (
    <Flex minH="90vh" >
      <Flex w="15%" justifyContent="center" alignItems="center" h="90vh">
        <CommunitySideBar selectedTab={choice} />
      </Flex>
      <Flex w="65%">
        {loading ? (
          <Flex
            w="100%"
            minHeight="90vh"
            flexDirection="column"
            alignItems="center"
            justifyContent="center"
          >
            <CircularProgress isIndeterminate color="teal" />
          </Flex>
        ) : (
            
          <Flex
            flexDirection="column"
            justifyContent="start"
            alignItems="start"
          >
            <Flex flexDirection="column" justifyContent="start" alignItems="start" marginLeft="60%" width="200%" marginTop="10%" fontSize="28px" letterSpacing="1.2px">
                    <center>Events of <b>{communityDetails.name}</b> for <b>{userid}</b></center>    
            </Flex>

            <VStack spacing={8} align="stretch" marginLeft="45%" width="130%" marginTop="15%">
              {events.map((event) => (
                <Box
                  key={event.eventId}
                  p={5}
                  shadow="md"
                  borderWidth="1px"
                  borderRadius="lg"
                  overflow="hidden"
                >
                  <HStack justify="space-between">
                    <Heading fontSize="xl">{event.eventName}</Heading>
                    <Badge borderRadius="full" px="2" colorScheme="teal">
                      {event.eventType}
                    </Badge>
                  </HStack>
                  <Text mt={2} fontSize="sm" color="gray.500">
                    {event.shortDescription}
                  </Text>
                  <Text mt={2}>{event.description}</Text>
                  <Text mt={2}>
                    <strong>Location:</strong> {event.location}
                  </Text>
                  <SimpleGrid columns={2} spacing={2} mt={2}>
                    <Text>
                      <strong>Start Time:</strong> {event.eventStartTime}
                    </Text>
                    <Text>
                      <strong>End Time:</strong> {event.eventEndTime}
                    </Text>
                    <Box justifyContent="center" width="350%">
                        <br />
                        <Button colorScheme="teal" size="sm" onClick={() => handleViewDetails(event.eventId)}>
                            View Details
                        </Button>
                    </Box>
                    
                  </SimpleGrid>
                </Box>
              ))}
            </VStack>
          </Flex>
        )}
      </Flex>
      <Flex w="20%">
        <CommunityMembers />
      </Flex>
    </Flex>
  );
}

export default CommunityEvents;
