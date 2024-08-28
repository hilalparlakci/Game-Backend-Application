Currently working on World CUp Tournament part.

1. User Progress 

1.1 Features

A new user begins with 5,000 coins from level 1.

Every new user is randomly assigned to one of these five countries - Turkey, the United
States, the United Kingdom, France, and Germany - which cannot be changed later.

User progress (level, coins, country) should be kept in MySQL Database, which is our main
persistent storage.

After completing each level, the user advances to the next level and receives 25 coins.

1.2 Flow and Requests

CreateUserRequest: This request creates a new user, returning a unique user ID, level, coins, and country.

UpdateLevelRequest: This request is sent by the client after each level completion. It updates the user's level and coins. Returns updated progress data.

2. World Cup Tournament

This time-limited competition allows users to compete against each other, represent their countries, and earn rewards.

2.1 Features

The tournament runs daily from 00:00 to 20:00 (UTC). A new one starts automatically the next day.

Users must be at least level 20 and pay 1,000 coins to participate.

Each tournament group should include 5 users, one from each country.

The competition for a group begins when five users from different countries are matched. Each level passed increases user score by 1 after the group begins.

Users compete within their groups until the tournament ends.

Users can claim rewards based on their rankings in their group after the tournament
ends.

Users cannot enter a new tournament if they haven't claimed their last tournament's
rewards.
