import re
import os

class F_Generator():

    def __init__(self,msg):
        self.pattern = msg
        a=ord('a')
        self.letters = [chr(i) for i in range(a,a+26)]

        self.is_valid = True
        self.check_valid()

    def create_range_format(self,lines):
        print('Creating file of the formatting...{Letter-Letter}')
        with open('./new_words.txt', 'w+', encoding='utf-8') as w:
            for line in lines:
                for i in range(self.letters.index(self.pattern[0]), self.letters.index(self.pattern[2]) + 1):
                    if self.letters[i] == line.lower().split()[1][0]:
                        count = line.split()[-1]
                        for i in range(int(count) + 1):
                            w.write(line.split()[1] + " ")

                        w.write("\n")

    def create_indexing_format(self,lines):
        print('Creating file of the formatting...{LetterLetterLetter}')
        with open('./new_words.txt', 'w+', encoding='utf-8') as w:
            for line in lines:
                for i in self.pattern:
                    if i == line.lower().split()[1][0]:
                        count = line.split()[-1]
                        for i in range(int(count) + 1):
                            w.write(line.split()[1] + " ")

                        w.write("\n")

    def check_valid(self):
        print('Checking Validity of sent message...')
        if self.letters == []:
            self.is_valid = False
        else:
            for i in self.pattern:
                if i in self.letters:
                    continue

                if self.pattern[1] == '-' and len(self.pattern) == 3:
                    continue
                else:
                    print('invalid input')
                    self.is_valid = False
                    break

    def create_file(self):
        if self.is_valid:
            print('Creating File')
            with open('./words.txt','r',encoding='utf-8') as f:
                lines = f.readlines()
                if len(self.pattern) == 3:
                    if self.pattern[1] == '-':
                        self.create_range_format(lines)


                    else:
                        self.create_indexing_format(lines)

                else:
                    self.create_indexing_format(lines)

            print('File Created')

        else:
            return 'invalid input'
