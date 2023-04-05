document.getElementById('login-form').addEventListener('submit', async (event) => {
    event.preventDefault();
    
    const clientId = 'proc-cnab-web';
    const clientSecret = 'web123';
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('http://localhost:8080/oauth/token', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Authorization': 'Basic ' + btoa(clientId + ':' + clientSecret),
            },
            body: `grant_type=password&username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('access_token', data.access_token); 
            window.location.href = '/home.html'; 
        } else {
            throw new Error('Falha na autenticação');
        }
    } catch (error) {
        alert(error.message);
    }
});
