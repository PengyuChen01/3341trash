

/* Question 1
Write a Quandary function int isList(Q) that returns a nonzero value if the input, which
can be any Quandary value, is a list, and otherwise returns 0. Recall that a value is a list if
it is nil or if it is a reference r such that right(r) is a list. Hint: You’ll need the built-in
function int isAtom(Q)
*/
int isList(Q input){
    if(isAtom(input) == 1){
        return isNil(input);
    }
    else{
        return isList(right((Ref)input));
    }
    return 0;
}

/* Question 2
Write a function Ref append(Ref, Ref) that takes as input two lists and returns a new list
that contains the first list’s elements followed by the second list’s elements. You can assume
the inputs are lists. For example:
append((3 . (4 . nil)), ((56 . (5 . nil)) . nil) . (26 . (2 . ((8 . nil).
nil))))
evaluates to
(3 . (4 . (((56 . (5 . nil)) . nil) . (26 . (2 . ((8 . nil) . nil))))))
*/
Ref append(Ref list , Ref list2){
    if (isNil(list) == 1){
        return list2;
    }
    return left(list). append((Ref)right(list), list2); 
}
/* Question 3
Write a Quandary function Ref reverse(Ref) that takes as input a list and returns a list
with the same elements, but in reverse order. You can assume the input to reverse is a list.
For example:
reverse((3 . (4 . (((56 . (5 . nil)) . nil) . (26 . (2 . ((8 . nil) .
nil)))))))
evaluates to
((8 . nil) . (2 . (26 . (((56 . (5 . nil)) . nil) . (4 . (3 . nil))))))
Hint: It may help to call your append function
*/
Ref reverse(Ref list){
    if (isNil(list) == 1){
        return list;
    }
    return append(reverse((Ref)right(list)), left(list).nil);
}

/* Question 4
Define a Quandary function int isSorted(Ref) that takes as input a list of lists and returns
a nonzero value if and only if the input list’s elements are ordered by length from least to
greatest. You can assume the input is a list of lists.
isSorted((3 . (5 . (5 . nil))) . ((2 . (8 . nil)) . ((6 . (7 . (4 . nil)))
. ((2 . (3 . (56 . (92 . nil))) . nil)))))
evaluates to 0, while
isSorted((3 . (5 . nil)) . ((2 . (8 . nil)) . ((6 . (7 . (4 . nil))) .
((2 . (3 . (56 . (92 . nil))) . nil)))))
evaluates to a nonzero value.
*/
int isSorted(Ref list){
    if (isNil(list) == 1 || isNil((Ref)right(list)) == 1){
        return 1;
    }
    
    if (length((Ref)left(list)) > length((Ref)left((Ref)right(list)))){
        return 0;
    }
    return isSorted((Ref)right(list));
}

int length(Ref list){
    if (isNil(list) == 1){
        return 0;
    }
    return 1 + length((Ref)right(list));
}

/*Question 7 */
int sameLength(Ref list, Ref list2){
    
    if(isNil(list) == 1 && isNil(list2) == 1){
        return 1;
    }
    if (isNil(list) != isNil(list2)) {
        return 0;
    }
     return sameLength((Ref)right(list), (Ref)right(list2));
}