export const getInterests= async () => {
    const requestOptions = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('BearerToken')
        }
    }

    try {
        const response = await fetch('https://commune-dev-csci5308-server.onrender.com/interest', requestOptions);

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error fetching interests:', error);
        return null;
    }
}

export const postCommunityInterest = async (communityID, interestID) => {
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('BearerToken')
        },
    };

    try {
        await fetch(`https://commune-dev-csci5308-server.onrender.com/community/${communityID}/interest?interest_id=${interestID}`, requestOptions);

        return true;
    } catch (error) {
        console.error('Error posting community interests:', error);
        return null;
    }
}