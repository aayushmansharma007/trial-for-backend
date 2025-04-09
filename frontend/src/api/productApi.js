const BASE_URL = 'https://trial-for-backend.onrender.com';

export const uploadImage = async (file) => {
    const formData = new FormData();
    formData.append('file', file);
    
    const response = await fetch(`${BASE_URL}/api/products/upload-image`, {
        method: 'POST',
        body: formData,
    });
    
    if (!response.ok) {
        throw new Error('Failed to upload image');
    }
    
    return await response.json();
};

export const createProduct = async (productData) => {
    const response = await fetch(`${BASE_URL}/api/products`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(productData),
    });
    
    if (!response.ok) {
        throw new Error('Failed to create product');
    }
    
    return await response.json();
};

export const getProducts = async () => {
    const response = await fetch(`${BASE_URL}/api/products`);
    
    if (!response.ok) {
        throw new Error('Failed to fetch products');
    }
    
    return await response.json();
};