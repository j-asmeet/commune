import { Tab, TabList, Tabs } from '@chakra-ui/react';
import React from 'react';
import { NavLink, useParams } from 'react-router-dom';

function CommunitySideBar({ selectedTab }) {
    let { cid } = useParams();
    return (
        
            <Tabs
                index={selectedTab}
                orientation="vertical"
                backgroundColor="#050A30"
                pt="12px"
                pr="0px"
                variant="unstyled"
                w="100%"
                justifyContent="center"
                h="90vh"
            >
                <TabList gap="8px" mt="25vh">
                    <NavLink to={`/community/${cid}/`}>
                        <Tab w="100%" fontWeight="medium" color="white" _selected={{ bg: "teal", color: "white", borderRadius: "4px" }}>General</Tab>
                    </NavLink>
                    <NavLink to={`/community/${cid}/posts`}>
                        <Tab w="100%" fontWeight="medium" color="white" _selected={{ bg: "teal", color: "white", borderRadius: "4px" }}>Posts</Tab>
                    </NavLink>
                    <NavLink to={`/community/${cid}/events`}>
                        <Tab w="100%" fontWeight="medium" color="white" _selected={{ bg: "teal", color: "white", borderRadius: "4px" }}>Events</Tab>
                    </NavLink>
                </TabList>
            </Tabs>

    );
}

export default CommunitySideBar;