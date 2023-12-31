# BoggleLetter class
"""Implements the functionality of a letter in Boggle."""

# import relevant classes and modules
from graphics import *

class BoggleLetter:
    """A Boggle letter has several attributes that define it:
       *  _row, _col coordinates indicate its position in the grid (ints)
       *  _textObj denotes the Text object from the graphics module,
          which has attributes such as size, style, color, etc
          and supports methods such as getText(), setText() etc.
       *  _color (str) denotes the color attribute:  a boggle letter turns
          blue when clicked, and is black by default or when unclicked.
          In a continuing word, previously clicked letters are green.
    """

    # add more attributes if needed!
    __slots__ = ['_col', '_row', '_textObj', '_color']

    def __init__(self, col=-1, row=-1, letter="", color="black"):
        # needed for standalone testing (can safely ignore)
        xInset = 50; yInset = 50; size = 50

        # set row and column attributes
        self._col = col
        self._row = row

        # call textObj setter
        self.textObj = Text(Point(xInset + size * col + size / 2,
                                  yInset + size * row + size / 2), letter)

        # call color setter
        self.color = color

    # properties (getter methods) for letter class
    @property
    def textObj(self):
        """Returns _textObj attribute (a Text object)"""
        return self._textObj

    @property
    def letter(self):
        """Returns letter (text of type str) associated with property textObj"""
        return self.textObj.getText()

    @property
    def col(self):
        """Returns _col coordinate (int) attribute"""
        return self._col

    @property
    def row(self):
        """Returns _row coordinate (int) attribute"""
        return self._row

    @property
    def color(self):
        """Returns color (str) attribute"""
        return self._color

    @property
    def isClicked(self):
        """Returns False if color is "black" (default), else returns True"""
        return self.color != "black"

    # setter methods for BoggleLetter class
    @letter.setter
    def letter(self, char):
        """Sets the text on the BoggleLetter to char (str) by setting the text
        of the Text object"""
        self.textObj.setText(char)

    @textObj.setter
    def textObj(self, textObj, size=20, style="bold"):
        """Sets the _text attribute to a Text object textObj, and sets
        provided size and style"""
        self._textObj = textObj
        self._textObj.setSize(size)
        self._textObj.setStyle(style)

    @color.setter
    def color(self, col):
        """Sets color of letter by modifying _color and _textObj
        attributes appropriately."""
        self._color = col
        self._textObj.setTextColor(col)

    # click and unclick methods that are useful in play
    def unclick(self):
        """Unclick letter by resetting color to black"""
        self.color = "black"

    def click(self):
        """Click letter by setting color to blue"""
        self.color = "blue"

        # test for adjacency
    def isAdjacent(self, other):
        """Given a BoggleLetter other, check if other is adjacent to self.
        Returns True if they are adjacent, and otherwise returns False.
        Two letters are considered adjacent if they are not the same, and
        if their row and col coordinates differ by at most 1.
        >>> BoggleLetter(1, 1, "A").isAdjacent(BoggleLetter(2, 2, "B"))
        True
        >>> BoggleLetter(1, 1, "C").isAdjacent(BoggleLetter(1, 3, "D"))
        False
        """
        l1Col, l1Row = self.col, self.row
        l2Col, l2Row = other.col, other.row
        notSame = l1Col != l2Col and l1Row != l2Row
        colCheck= abs(l1Col - l2Col) <= 1
        rowCheck= abs(l1Row - l2Row) <= 1
        return notSame and colCheck and rowCheck

    def __str__(self):
        l, col, row = self.letter, self.col, self.row
        return "{} at Board position ({}, {})".format(l,col,row)

    def __repr__(self):
        l, col, row, color = self.letter, self.col, self.row,  self.color
        return "BoggleLetter({}, {}, '{}', '{}')".format(col,row,l,color)

if __name__ == "__main__":

    from doctest import testmod
    testmod()

    ## uncomment for testing BoggleLetter class
    #from board import Board
    #
    #win = GraphWin("Boggle", 400, 400)
    #board = Board()
    #board.drawBoard(win)
    #
    #let1 = BoggleLetter(1, 1, "A")
    #let1.textObj.draw(win)
    #print(let1)
    #print(repr(let1))
    #
    #let2 = BoggleLetter(1, 2)
    #let2.letter = 'B'
    #let2.textObj.draw(win)
    #print(let2)
    #print(repr(let2))
    #let2.click()
    #print(let2)
    #print(repr(let2))
    #
    #let3 = BoggleLetter(3, 1, "C", color="green")
    #let3.textObj.draw(win)
    #let3.unclick()
    #print(let3)
    #print(repr(let3))
    #
    #print("A <-> B: {}".format(let1.isAdjacent(let2)))
    #print("B <-> A: {}".format(let2.isAdjacent(let1)))
    #print("C <-> C: {}".format(let3.isAdjacent(let3)))
    #print("C <-> A: {}".format(let3.isAdjacent(let1)))
    #print("B <-> C: {}".format(let2.isAdjacent(let3)))
    #
    ## pause and wait for mouse click before exiting
    #point = win.getMouse()
