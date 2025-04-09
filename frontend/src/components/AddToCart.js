import axios from 'axios';

const addToCart = async (productId) => {
    try {
        const userId = localStorage.getItem('userId');
        
        if (!userId) {
            throw new Error('User not authenticated');
        }

        // Convert userId and productId to numbers to ensure they're sent as proper numbers
        const params = new URLSearchParams({
            userId: Number(userId),
            productId: Number(productId),
            quantity: 1
        });

        const response = await axios({
            method: 'POST',
            url: 'https://trial-for-backend.onrender.com/api/cart/add',
            params: params,
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        });

        if (response.data) {
            return response.data;
        } else {
            throw new Error('No data received from server');
        }
    } catch (error) {
        if (error.response?.data) {
            console.error('Server error:', error.response.data);
            throw new Error(error.response.data);
        } else {
            console.error('Request error:', error.message);
            throw new Error('Failed to add item to cart');
        }
    }
};

// Usage example:
const handleAddToCart = async (productId) => {
    try {
        const result = await addToCart(productId);
        console.log('Added to cart:', result);
        alert('Item added to cart successfully');
    } catch (error) {
        console.error('Error adding to cart:', error);
        alert(error.message);
    }
};

export { addToCart, handleAddToCart };
