const http = require('http');
const app = require('./app');
const port = process.env.PORT;


const server = http.createServer(app);
server.listen(port, () => {
    console.log(`Server running on port ${port}`);
});
// const express = require('express');
// const app = express();
// const port = process.env.PORT || 5001; // Change 5001 to 5001 or another port

// app.listen(port, () => {
//   console.log(`Server is running on port ${port}`);
// });
