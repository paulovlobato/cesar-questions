#!/usr/bin/python3

__author__ = "Paulo Sarmento"
__email__ = "pvlobato@gmail.com"

def hasTypos(string_1: str, string_2: str):
    ''' 
        Checks if a string has any typos in relation to the other

        of the largest string

        Parameters
        ----------
        string_1 : str
        string_2 : str

        Returns
        -------
        boolean
            returns a boolean
    '''

    if (string_1 == string_2):
        return False

    _diff = 0
    _string_1_aux = string_1
    _string_2_aux = string_2

    if (len(string_1) == len(string_2)):
        for idx in range(len(string_1)):
            if (string_2[idx] != string_1[idx]):
                _diff += 1

    if (len(string_1) > len(string_2)):
        _diffLength = len(string_1) - len(string_2)
        
        while (_diffLength > 0):
            _string_2_aux += " "
            _diffLength -= 1

            for idx in range(len(string_1)):
                if (string_1[idx] != _string_2_aux[idx]):
                    _string_2_aux = _string_2_aux[0: idx:] + string_1[idx] + _string_2_aux[idx: (len(_string_2_aux) - 1): ]
                    _diff +=1

    else:
        _diffLength = len(string_2) - len(string_1)

        while (_diffLength > 0):
            _string_1_aux += " "
            _diffLength -= 1

            for idx in range(len(string_2)):
                if (string_2[idx] != _string_1_aux[idx]):
                    _string_1_aux = _string_1_aux[0: idx:] + string_1[idx] + _string_1_aux[idx: (len(_string_1_aux) - 1): ]

    return _diff == 1

tests = [
    ['pale', 'ple', True],
    ['pales', 'pale', True],
    ['pale', 'bale', True],
    ['pale', 'bake', False]
]

for x in tests:
    assert x[2] == hasTypos(x[0], x[1])