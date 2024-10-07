# Telegram Bot Project

This is a Telegram bot developed in **Java** using **Maven**, hosted on a **Raspberry Pi**. The bot is designed to be modular, easy to extend, and handles functionalities like finding dog photos on demand. Sensitive information, such as the bot token, is stored securely in a `bot.properties` file.

[!Example](chat_history.png)
[Chat History](chat_history.pdf)

## Features

- Fetch random dog photos 🐶.
- Modular design with separate classes for handling different functionalities.
- Secure storage of sensitive information in `bot.properties`.
- Comprehensive error handling to ensure the bot remains operational.
- Hosted on a Raspberry Pi for continuous 24/7 availability.

## Project Structure

The bot follows a **modular architecture**, with each feature encapsulated in its own class. This makes the code easy to maintain and extend.

- **MainBot.class**: Core logic of the bot.
- **HandleInlineQuery.class**: Handles Telegram inline queries.
- **DogPhotoService.class**: Fetches random dog photos from an external API.
- **ErrorHandler.class**: Centralized error logging and handling.
- **ConfigLoader.class**: Loads configuration data from the `bot.properties` file.

### Future Modifications

The modular structure of this bot makes it easy to add more features in the future. Simply add new classes for additional commands or services, such as:

- **CatPhotoService.class** for fetching cat photos.
- **WeatherService.class** for providing weather updates.
- **JokeService.class** to tell random jokes.

## Prerequisites

- **Java 11** or higher
- **Maven**
- A Raspberry Pi with **SSH** access
- A **Telegram bot token**

## Setup

1. **Clone the repository:**
    ```sh
    git clone https://github.com/yourusername/telegram-bot-project.git
    cd telegram-bot-project
    ```

2. **Create a `bot.properties` file**:
    In the `src/main/resources` directory, create a `bot.properties` file and add your bot token:
    ```properties
    bot.token=YOUR_BOT_TOKEN
    ```

3. **Build the project using Maven**:
    ```sh
    mvn clean install
    ```

4. **Copy the generated `.jar` file to your Raspberry Pi**:
    ```sh
    scp target/telegram-bot.jar pi@your-raspberry-pi-ip:/home/pi/
    ```

5. **SSH into your Raspberry Pi and run the bot**:
    ```sh
    ssh pi@your-raspberry-pi-ip
    java -jar /home/pi/telegram-bot.jar
    ```

## Running the Bot on Raspberry Pi

To keep the bot running continuously on your Raspberry Pi:

1. **Install the bot as a service**:
    Create a new service file:
    ```sh
    sudo nano /etc/systemd/system/telegram-bot.service
    ```
    Add the following configuration:
    ```ini
    [Unit]
    Description=Telegram Bot

    [Service]
    ExecStart=/usr/bin/java -jar /home/pi/telegram-bot.jar
    Restart=always
    User=pi

    [Install]
    WantedBy=multi-user.target
    ```

2. **Start the bot service**:
    ```sh
    sudo systemctl start telegram-bot
    sudo systemctl enable telegram-bot
    ```

The bot will now start on boot and run in the background.

## Error Handling

The bot includes robust error handling across all modules, ensuring it remains operational even when unexpected errors occur. Errors are logged and can be reviewed to improve performance and debug issues.

## Contributing

Contributions are welcome! Feel free to fork the repository and submit a pull request. For major changes, please open an issue first to discuss what you'd like to change.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.
