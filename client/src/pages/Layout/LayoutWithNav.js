import React from 'react';
import { Outlet } from 'react-router-dom';
import NavBar from '../../components/NavBar/NavBar';

function LayoutWithNav() {
    return (
        <>
            <NavBar />
            <Outlet />
        </>
    );
}

export default LayoutWithNav;