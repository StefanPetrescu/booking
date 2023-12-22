**Initial Request:**
Provide the following functionality:

Creating, deleting and updating a booking
Creating, deleting and updating a block

Bookings can be updated (dates, guest data), canceled and rebooked. Blocks can be created, updated and deleted. Blocks can overlap with each other. 
Provide logic to prevent bookings overlapping (in terms of dates and property) with other bookings or blocks.

**Additional Details:**

I added the postman collection in the resources package. I only managed to test manually in the given time, without unit tests. If you want to check how I write unit tests, just let me know and I can add them.
I also added more functionality: If there is a booking made before, you can't simply create a block (left a comment in the code too). I had 2 options: either implement like mentioned before, or if a block was created
I should have deleted all the bookings from that period.
