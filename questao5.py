#!/usr/bin/python3

__author__ = "Paulo Sarmento"
__email__ = "pvlobato@gmail.com"

'''
    Since Python does not not have a builtin LinkedList
    it was created the concept of nodes to better simulate the LinkedList behaviour
'''
# Source: https://gist.github.com/ashwinwadte/6c7bb7c6464760a2c0733f8dfa33682e

class Node:
    ''' LinkedList's Node '''
    def __init__(self, data=None):
        self.data = data
        self.next_val = None

class LinkedList:
    ''' LinkedList '''
    def __init__(self):
        self.head_val = None

    def show_list(self):
        current_node = self.head_val
        while current_node is not None:
            print('--> ', current_node.data)
            current_node = current_node.next_val

class Email:
    def __init__(self, mail_from: str = None, mail_to: str = None, message: str = None):
        self.mail_from = mail_from
        self.mail_to = mail_to
        self.message = message
    
    def __str__(self):
        return f"Mail from: {self.mail_from}, to: {self.mail_to} --- {self.message}"

def removeDuplicates(linked_list: LinkedList):
    ''' 
        Remove the duplicates in a LinkedList


        It's time complexity is O(n), since it will run for the length of the LinkedList, comparing it to the hashset

        Parameters
        ----------
        linked_list1 : LinkedList

        Returns
        -------
        LinkedList
            returns the trimmed original linked list
    '''
    previous_node = linked_list.head_val
    current_node = previous_node.next_val

    keys = set([previous_node.data])

    while current_node:
        data = current_node.data
        
        if data in keys:
            previous_node.next_val = current_node.next_val
            current_node = current_node.next_val
        else:
            keys.add(data)
            previous_node = current_node
            current_node = current_node.next_val
        
    print("Returned list, without duplicates: ")
    linked_list.show_list()
    return linked_list

thread = LinkedList()
email1 = Email('mmcp@cesar.org.br', 'pvlobato@gmail.com', 'Paulo, bem-vindo à CESAR')
email2 = Email('pvlobato@gmail.com', 'mmcp@gmail.com', 'Tenho dúvidas em relação às questões!')
email3 = email1

node1 = Node(email1)
node2 = Node(email2)
node3 = Node(email3)

thread.head_val = node1
thread.head_val.next_val = node2
node2.next_val = node3

print("Original list, with duplicates")
thread.show_list()

removeDuplicates(thread)