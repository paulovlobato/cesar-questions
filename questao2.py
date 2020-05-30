#!/usr/bin/python3

__author__ = "Paulo Sarmento"
__email__ = "pvlobato@gmail.com"

def isPartialPermutation(original: str, possible_permutation: str):
    ''' 
        Checks for partial permutation of two strings

        It's time complexity is O(n), since it will run only one time for the length
        of the possible permutation string

        Parameters
        ----------
        original : str
        possible_permutation : str

        Returns
        -------
        boolean
            returns a boolean
    '''
    MAX_ALLOWED_WORD = 3
    isPermutation = False
    changed_occurrences = 0

    if (len(original) != len(possible_permutation)):
        return

    if (possible_permutation[0] == original[0]):
        if (len(possible_permutation) > MAX_ALLOWED_WORD):
            for idx in range(len(possible_permutation)):
                if (original[idx] != possible_permutation[idx]):
                    changed_occurrences += 1

        if (changed_occurrences <= ((2/3) * len(possible_permutation))):
            isPermutation = True

    # print(isPermutation)
    return isPermutation

tests = [
    ['you', 'yuo', True],
    ['probably', 'porbalby', True],
    ['despite', 'desptie', True],
    ['moon', 'nmoo', False],
    ['misspellings', 'mpeissngslli', False]
]

for x in tests:
    assert x[2] == isPartialPermutation(x[0], x[1])