// LOGIN FORM
const loginForm = document.getElementById('loginForm');
if (loginForm) {
    loginForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const email = document.getElementById('loginEmail').value;
        const password = document.getElementById('loginPassword').value;
        const message = document.getElementById('loginMessage');

        fetch('http://localhost:8080/api/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email: email, password: password })
        })
        .then(response => {
            if (response.ok) {
                return response.text(); // Get userId
            } else {
                return response.text().then(error => { throw new Error(error); });
            }
        })
        .then(userId => {
            message.textContent = 'Logged in! Welcome!';
            message.style.color = 'green';
            localStorage.setItem('userId', userId);
            window.location.href = 'cars.html';
        })
        .catch(error => {
            message.textContent = error.message;
            message.style.color = 'red';
        });
    });
}

// REGISTER FORM
const registerForm = document.getElementById('registerForm');
if (registerForm) {
    registerForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const username = document.getElementById('registerName').value; // Match HTML id
        const email = document.getElementById('registerEmail').value;
        const password = document.getElementById('registerPassword').value;
        const message = document.getElementById('registerMessage');

        if (!email.includes('@') || !email.includes('.')) {
            message.textContent = 'Please enter a valid email (example@domain.com)';
            message.style.color = 'red';
            return;
        }

        fetch('http://localhost:8080/api/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ 
                name: username, 
                email: email, 
                password: password 
            })
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                return response.text().then(error => { throw new Error(error); });
            }
        })
        .then(result => {
            message.textContent = result;
            message.style.color = 'green';
        })
        .catch(error => {
            message.textContent = error.message;
            message.style.color = 'red';
        });
    });
}

// CARS PAGE - SHOW AVAILABLE CARS
function showCars() {
    const carsTableBody = document.getElementById('carsTableBody');
    fetch('http://localhost:8080/api/cars')
    .then(response => response.json())
    .then(cars => {
        carsTableBody.innerHTML = '';
        cars.forEach(car => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${car.id}</td>
                <td>${car.name}</td>
                <td>${car.model}</td>
            `;
            carsTableBody.appendChild(row);
        });
    })
    .catch(error => {
        console.log('Error getting cars:', error);
    });
}

// CARS PAGE - SHOW USER'S BOOKINGS
function showBookings() {
    const userId = localStorage.getItem('userId');
    const bookingsTableBody = document.getElementById('bookingsTableBody');

    if (!userId) {
        bookingsTableBody.innerHTML = '<tr><td colspan="4">Please login to see your bookings</td></tr>';
        return;
    }

    fetch('http://localhost:8080/api/bookings/user?userId=' + userId)
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Could not load bookings');
        }
    })
    .then(bookings => {
        bookingsTableBody.innerHTML = '';
        bookings.forEach(booking => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${booking.id}</td>
                <td>${booking.car.id}</td>
                <td>${booking.startDate}</td>
                <td>${booking.endDate}</td>
            `;
            bookingsTableBody.appendChild(row);
        });
    })
    .catch(error => {
        bookingsTableBody.innerHTML = `<tr><td colspan="4">${error.message}</td></tr>`;
    });
}

// CARS PAGE - BOOK A CAR
const bookingForm = document.getElementById('bookingForm');
if (bookingForm) {
    bookingForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const userId = localStorage.getItem('userId');
        const message = document.getElementById('bookingMessage');

        if (!userId) {
            message.textContent = 'Please login to book a car';
            message.style.color = 'red';
            return;
        }

        const carId = document.getElementById('carId').value;
        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;

        fetch('http://localhost:8080/api/bookings?userId=' + userId, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ 
                car: { id: carId }, 
                startDate: startDate,
                endDate: endDate,
                totalPrice: 0 
            })
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                return response.text().then(error => { throw new Error(error); });
            }
        })
        .then(result => {
            alert(result);
            showBookings();
            message.textContent = '';
        })
        .catch(error => {
            message.textContent = error.message;
            message.style.color = 'red';
        });
    });
}

// Load cars and bookings when on the cars page
if (window.location.pathname.endsWith('cars.html')) {
    showCars();
    showBookings();
}