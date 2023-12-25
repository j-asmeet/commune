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
                w="30vh"
                justifyContent="center"
                h="90vh"
                marginRight={"10px"}
            >
                <TabList gap="8px" mt="25vh">
                    <NavLink to={`/community/${cid}/admin`}>
                        <Tab w="100%" fontWeight="medium" color="white" _selected={{ bg: "teal", color: "white", borderRadius: "4px" }}>General</Tab>
                    </NavLink>
                    <NavLink to={`/community/${cid}/admin/manage-members`}>
                        <Tab w="100%" fontWeight="medium" color="white" _selected={{ bg: "teal", color: "white", borderRadius: "4px" }}>Manage Members</Tab>
                    </NavLink>
                    <NavLink to={`/community/${cid}/admin/settings`}>
                        <Tab w="100%" fontWeight="medium" color="white" _selected={{ bg: "teal", color: "white", borderRadius: "4px" }}>Settings</Tab>
                    </NavLink>
                </TabList>
            </Tabs>

    );
}

export default CommunitySideBar;