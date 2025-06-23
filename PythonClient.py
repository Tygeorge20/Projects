import xmlrpc.client, sys
import time 
import os

# Specify Server name when running i.e "python3 PythonClient.py localhost"
with xmlrpc.client.ServerProxy("http://"+sys.argv[1]+":8889") as proxy:
    # print(proxy.sample.Lookup(53477)) # UNCOMMENT TO TEST BUY FUNCTIONALITY
    # print(proxy.sample.Buy(12498)) # UNCOMMENT TO TEST LOOKUP FUNCTIONALITY
    # print(proxy.sample.Search('Soccer')) # UNCOMMENT TO TEST SEARCH FUNCTIONALITY

    # Search Testing
    start_time = time.time()
    for i in range(500):
        proxy.sample.Search('College')

    end_time = time.time()
    total_time = end_time - start_time
    print("The average response time per client search is: ", total_time/500, " seconds")

    # Buy Testing
    start_time = time.time()
    os.system("java Functions restock 53477 1500")
    for i in range(500):
        proxy.sample.Buy(53477)

    end_time = time.time()
    total_time = end_time - start_time
    print("The average response time per client buy is: ", total_time/500, " seconds")
