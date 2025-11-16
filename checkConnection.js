const mongoose = require("mongoose");

const uri = "mongodb://atlas-sql-68f2466cef563236f24acb9c-0pgsak.a.query.mongodb.net/sample_mflix?ssl=true&authSource=admin"; // Replace with your actual MongoDB Atlas connection string (including database name)

mongoose.connect(uri);

const db = mongoose.connection;

db.on("connected", () => {
  console.log("✅ MongoDB connection successful!");
});

db.on("error", (err) => {
  console.error("❌ MongoDB connection error:", err);
});

db.on("disconnected", () => {
  console.log("⚠️ MongoDB disconnected");
});

module.exports = db;
