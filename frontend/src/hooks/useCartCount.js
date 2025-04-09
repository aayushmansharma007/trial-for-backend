import { useState, useEffect } from 'react';
import axios from 'axios';

export const useCartCount = () => {
    const [cartCount, setCartCount] = useState(0);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchCartCount = async () => {
        try {
            const userId = localStorage.getItem('userId'); // or however you store the user ID
            
            if (!userId) {
                setCartCount(0);
                setLoading(false);
                return;
            }

            const response = await axios.get(`https://trial-for-backend.onrender.com/api/cart/${userId}`);
            const items = response.data;
            setCartCount(Array.isArray(items) ? items.length : 0);
            setError(null);
        } catch (error) {
            console.error('Error fetching cart count:', error);
            setError(error.message);
            setCartCount(0);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchCartCount();
    }, []);

    return { cartCount, loading, error, refreshCartCount: fetchCartCount };
};