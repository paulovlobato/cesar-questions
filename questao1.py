#!/usr/bin/python3

__author__ = "Paulo Sarmento"
__email__ = "pvlobato@gmail.com"

def replaceSpacesWith(input: str, toReplace: str):
    ''' 
        Replaces the spaces from given input 
        with the desired string

        Consider it want changes made to the "input" itself, not assign it to any other variable
        Even though python strings are immutable, we can treat them as if they were "lists", and make such modifications
    
        It's complexity is O(n²), since, in this proposed solution, it will run twice for the input
        (to append to the end of the string, and to remove the first string)

        Parameters
        ----------
        input : str
        toReplace : str

        Returns
        -------
        str
            returns the same string obj, but with the replaced characters
    '''
    STRING_SIZE = len(input)

    for c in input:
        input += toReplace if (c == ' ') else c
    
    if len(input) > STRING_SIZE:
        input = input[0: 1:] + input[STRING_SIZE + 1::]

    # print(input)
    return input


input = "User is not allowed"
desiredOutput = "User&32is&32not&32allowed"

assert desiredOutput == replaceSpacesWith(input, '&32')