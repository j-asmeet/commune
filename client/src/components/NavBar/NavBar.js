import React from 'react';
import { Box, Button, Flex, Text, Menu, MenuButton, MenuList, MenuItem } from '@chakra-ui/react';
import { NavLink } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

function NavBar() {
    const bearerToken = localStorage.getItem('BearerToken');
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem('BearerToken');
        localStorage.removeItem('userID');
        localStorage.removeItem('fullName');

        navigate('/');
    };

    return (
        <Flex as="nav" alignItems="center" justify="space-between" h="10vh" w="100%" backgroundColor="#050A30">
            {/* Logo */}
            <NavLink to="/">
                <Text fontWeight="medium" color="white" fontSize="lg" ml="24px">Commune</Text>
            </NavLink>
            <Flex gap="24px" mr="40px" h="10vh" alignItems="center">

                {
                    bearerToken && (<Box>
                        <NavLink to='/dashboard'>
                            <Text fontWeight="medium" color="white" fontSize="lg">My Dashboard</Text>
                        </NavLink>
                    </Box>)
                }
                {bearerToken && (
                    <Menu>
                        {/* Community */}
                        <MenuButton fontWeight="medium" color="white" fontSize="lg">
                            <Text fontWeight="medium" color="white" fontSize="lg">Community</Text>
                        </MenuButton>
                        <MenuList backgroundColor="#050A30">
                            <NavLink to="/create-community">
                                <MenuItem backgroundColor="#050A30">
                                    <Text fontWeight="medium" color="white">Create Community</Text>
                                </MenuItem>
                            </NavLink>
                            <NavLink to="/user/my-community">
                                <MenuItem backgroundColor="#050A30">
                                    <Text fontWeight="medium" color="white"> My Communities</Text>
                                </MenuItem>
                            </NavLink>
                            <NavLink to="/communities">
                                <MenuItem backgroundColor="#050A30">
                                    <Text fontWeight="medium" color="white"> All Communities</Text>
                                </MenuItem>
                            </NavLink>
                        </MenuList>
                    </Menu>
                )}
                {bearerToken && (
                    <Menu>
                        {/* Events */}
                        <MenuButton fontWeight="medium" color="white" fontSize="lg">
                            <Text fontWeight="medium" color="white" fontSize="lg">Events</Text>
                        </MenuButton>
                        <MenuList backgroundColor="#050A30">
                            <NavLink to="/create-event">
                                <MenuItem backgroundColor="#050A30" >
                                    <Text fontWeight="medium" color="white">Create Event</Text>
                                </MenuItem>
                            </NavLink>
                            <NavLink to="/dashboard">
                                <MenuItem backgroundColor="#050A30">
                                    <Text fontWeight="medium" color="white">My Events</Text>
                                </MenuItem>
                            </NavLink>
                            <NavLink to="/event-list">
                                <MenuItem backgroundColor="#050A30" >
                                    <Text fontWeight="medium" color="white">All Events</Text>
                                </MenuItem>
                            </NavLink>
                        </MenuList>
                    </Menu>
                )}
                {/* Logout */}
                {bearerToken ? (
                    <Box>
                        <NavLink>
                            <Button fontWeight="medium" colorScheme="teal" variant="solid" fontSize="lg" mb="8px" onClick={handleLogout}>Logout</Button>
                        </NavLink>
                    </Box>
                ) : (
                    <NavLink to='/login'>
                        <Button fontWeight="medium" colorScheme="teal" variant="solid" fontSize="lg" mb="8px">Log In</Button>
                    </NavLink>
                )}
            </Flex>
        </Flex>
    );
}

export default NavBar;
