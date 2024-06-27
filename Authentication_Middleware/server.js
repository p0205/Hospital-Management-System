const http = require('http');
const app = require('./app');
const port = process.env.PORT;


const server = http.createServer(app);
server.listen(port, () => {
    console.log(`Server running on port ${port}`);
});
// const express = require('express');
// const app = express();
// const port = process.env.PORT || 5000; // Change 5000 to 5000 or another port

// app.listen(port, () => {
//   console.log(`Server is running on port ${port}`);
// });
