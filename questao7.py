#!/usr/bin/python3

__author__ = "Paulo Sarmento"
__email__ = "pvlobato@gmail.com"

'''
    Since Python does not not have a builtin LinkedList
    it was created the concept of nodes to better simulate the LinkedList behaviour
'''

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

    def append(self, data):
        ''' Add to end of LinkedList '''
        if self.head_val is None:
            self.head_val = data
        else:
            curr = self.head_val
            while curr.next_val is not None:
                curr = curr.next_val
            curr.next_val = data

class Email:
    def __init__(self, mail_from: str = None, mail_to: str = None, message: str = None):
        self.mail_from = mail_from
        self.mail_to = mail_to
        self.message = message
    
    def __str__(self):
        return f"Mail from: {self.mail_from}, to: {self.mail_to} --- {self.message}"

def removeDuplicates(linked_list: LinkedList):
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
        
    print("List, without duplicates: ")
    linked_list.show_list()

def find_intersection(linked_list1: LinkedList, linked_list2: LinkedList):
    ''' 
        Find the intersection between two linked lists

        Parameters
        ----------
        linked_list1 : LinkedList
        linked_list2 : LinkedList

        Returns
        -------
        LinkedList
            returns a new linked list with the found intersections
    '''
    intersection = LinkedList()
    if (linked_list1 is None or linked_list2 is None):
        return intersection
    
    curr1 = linked_list1.head_val

    while curr1:
        curr2 = linked_list2.head_val
        while curr2:
            if (curr2.data == curr1.data):
                intersection_node = Node(curr2.data)
                intersection.append(intersection_node)
                break
            curr2 = curr2.next_val
        curr1 = curr1.next_val
    print("Intersection between the LinkedLists: ")
    intersection.show_list()
    return intersection

thread = LinkedList()
thread2 = LinkedList()

email1 = Email('mmcp@cesar.org.br', 'pvlobato@gmail.com', 'Paulo, bem-vindo à CESAR')
email2 = Email('pvlobato@gmail.com', 'mmcp@gmail.com', 'Tenho dúvidas em relação às questões!')
email3 = email1

node1 = Node(email1)
node2 = Node(email2)
node3 = Node(email3)

thread.head_val = node1
thread.head_val.next_val = node2
node2.next_val = node3

thread2.head_val = Node(email2)

print("THREAD 1 ITEMS: ")
thread.show_list()
print("THREAD 2 ITEMS: ")
thread2.show_list()

find_intersection(thread, thread2)