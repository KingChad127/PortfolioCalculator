# PortfolioTracker
Track your trading transactions and basic portfolio details. User can enter their stock transactions and see all realized and unrealized gains per position. 

- Enter stock transactions
- View transaction history with additional filtering options
- See your portfolio's biggest winners and losers

# How to use
To get started, unzip the download file. Navigate to the contents of the unzipped directory. To run the shell script on MACOS and Linux, type "./run.sh" into a terminal.

### Using a transaction csv file
To use a csv file of transactions instead of entering individual transactions, follow this format:

transactiontype,ticker,price,quantity,date
buy,QQQM,144.91,3,06.28.2021

The first line must be exactly the same - this indicates to the program that a transaction csv file follows

Put each transaction on a separate line with transaction attributes in the specified order as shown in the header. The date must be entered as MM-DD-YYYY. Hyphens, periods, spaces, and back-slashes can be used for the date separators. 