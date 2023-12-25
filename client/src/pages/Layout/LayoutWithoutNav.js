import React from 'react';
import { Outlet } from 'react-router-dom';

function LayoutWithoutNav() {
    return (
        <Outlet />
    );
}

export default LayoutWithoutNav;