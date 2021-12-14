import time  # For calculation time of gram 
import re  # The regular expression library
import operator


# Read all corpus file  
file1 = open('bilim-is-basinda.txt', 'r') 
file2 = open('bozkirda.txt', 'r') 
file3 = open('degisim.txt', 'r') 
file4 = open('denemeler.txt', 'r') 
file5 = open('grimms-fairy-tales-p1.txt', 'r')

# Lists that hold the 1,2 and 3-gram values of words 
allContentList = []
uniGramList = []
biGramList = []
triGramList = []
# Dictionary that holds 1,2 and 3-gram values and their corresponding frequency values
frequencyDict = {}

# Function that removes punctuation marks and converts each word to lowercase and splits it by space.
def getAllContent(data) -> list:  
    return re.sub(r'[^\w\s+|\s+$]', '', data.lower()).strip().replace("\n", "").split(' ')


# Function that reads the files and adds the words to the fileContent list then return it
def readFiles(f1,f2,f3,f4,f5):
    fileContent = getAllContent(f1.read())
    fileContent += getAllContent(f2.read())
    fileContent += getAllContent(f3.read())
    fileContent += getAllContent(f4.read())
    fileContent += getAllContent(f5.read())
    return fileContent

# A function that takes the list of all words as a parameter and adds the words to the related list according to their n-gram values.
def nGramCreator(data, n):
    consecutiveWordList = []
    if n == 1:
        for i in range(len(data)):
            consecutiveWordList.append(data[i])
    elif n == 2:
        for i in range(len(data)-n+1):
            for j in range(n-1):
                consecutiveWordList.append(data[i+j] + ' ' + data[i+n-1])
    elif n == 3:
        for i in range(len(data)-n+1):
            for j in range(n-2):
                 consecutiveWordList.append(data[i+j] + ' ' + data[i+n-2] + ' ' + data[i+n-1])
    return consecutiveWordList

# Function that adds 1,2 and 3-grams and their frequency values to the map.
def frequencyCount(nGramList):
    for item in nGramList:
        if item not in frequencyDict:
            frequencyDict[item] = 0 
        frequencyDict[item] += 1


# Function that sorts 1,2 and 3-grams according to their frequency values and prints the 50 highest ones
def printTop50(freDict, nGram = 1, limit = 50):
    sortedList = sorted(freDict.items(), key = operator.itemgetter(1), reverse = True)
    print('---------- Top 50 Items ----------\n')
    for i in range(limit):
        print('Item: {} -> {}'.format((i+1), sortedList[i]))


allContentList = readFiles(file1, file2, file3, file4, file5)

allContentList = list(filter(None, allContentList)) # Remove empty items 


nGramTypeInput = int(input('Enter a n-gram number(n) between <1,2,3> : '))

print('\n\n')

if nGramTypeInput == 1:
    print("------------ UNIGRAM ------------\n") # 1-gram
    start_time = time.time()

    uniGramList = nGramCreator(allContentList, 1)
    frequencyCount(uniGramList)

    elapsed_time = time.time() - start_time
    print('Total running time: {} ms.'.format(int(elapsed_time*1000)))
    print("\n")

    printTop50(frequencyDict, 1)


elif nGramTypeInput == 2:
    print("------------ BIGRAM ------------\n") # 2-gram
    start_time = time.time()

    biGramList = nGramCreator(allContentList, 2)
    frequencyCount(biGramList)

    elapsed_time = time.time() - start_time
    print('Total running time: {} ms.'.format(int(elapsed_time*1000)))
    print("\n")

    printTop50(frequencyDict, 2)


elif nGramTypeInput == 3:
    print("------------ TRIGRAM ------------\n") # 3-gram
    start_time = time.time()

    triGramList = nGramCreator(allContentList, 3)
    frequencyCount(triGramList)

    elapsed_time = time.time() - start_time
    print('Total running time: {} ms.'.format(int(elapsed_time*1000)))
    print("\n")

    printTop50(frequencyDict, 3)









file1.close()
file2.close()
file3.close()
file4.close()
file5.close()
