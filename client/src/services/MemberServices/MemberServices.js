export const postMember = async (userid, communityID, userRole) => {
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('BearerToken')
        },
        body: JSON.stringify({ community_id: communityID, user_id: userid, user_role: userRole })
    };

    try {
        await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${communityID}/members`, requestOptions);

        return true;
    } catch (error) {
        console.error('Error posting member:', error);
        return null;
    }
}