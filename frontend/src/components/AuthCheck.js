import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export const useAuthCheck = () => {
    const navigate = useNavigate();
    
    useEffect(() => {
        const userId = localStorage.getItem('userId');
        const token = localStorage.getItem('token');
        
        if (!userId || !token) {
            navigate('/login');
        }
    }, [navigate]);
};

// Usage in cart component:
const Cart = () => {
    useAuthCheck(); // This will redirect to login if user is not authenticated
    
    // Rest of your cart component code...
};