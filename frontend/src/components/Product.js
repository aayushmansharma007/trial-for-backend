const addToCart = async (productId) => {
    try {
        // Get userId from your authentication context or localStorage
        const userId = localStorage.getItem('userId'); // or however you store the user ID
        
        if (!userId) {
            // Handle case where user is not logged in
            alert('Please log in to add items to cart');
            return;
        }

        const response = await axios.post(`https://trial-for-backend.onrender.com/api/cart/add`, null, {
            params: {
                userId: userId,
                productId: productId,
                quantity: 1
            }
        });

        if (response.data) {
            // Success handling
            alert('Product added to cart successfully');
            // Optionally update cart count
            updateCartCount();
        }
    } catch (error) {
        console.error('Error adding to cart:', error.response?.data || error.message);
        alert('Failed to add item to cart. Please try again.');
    }
};