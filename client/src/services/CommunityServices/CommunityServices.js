export const CreateNewCommunity= async (userid, name, description) => {
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('BearerToken')
        },
        body: JSON.stringify({ created_by: userid, name: name, description: description, display_image: "link" })
    };

    try {
        const response = await fetch('https://commune-dev-csci5308-server.onrender.com/community', requestOptions);

        const communityID = await response.text();
        return communityID;
    } catch (error) {
        console.error('Error creating community:', error);
        return null;
    }
}