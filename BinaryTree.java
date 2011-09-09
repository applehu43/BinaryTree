import java.util.*;

public class BinaryTree<E> {
  
  protected int btSize;
  protected int btHeight;
  protected Node<E> root; 
  
  public BinaryTree() {
    btSize = 0;
    btHeight = 0;
    root = null;   
  }
  
  public int getBTSize() {return btSize;}

  public int getBTHeight() {return btHeight;}
  
  public Node<E> getRoot() throws EmptyTreeException {
    if (root == null) throw new EmptyTreeException("Empty tree");
    return root;
  }
  
  public Node<E> getParent(Node<E> n) throws BoundaryException {
    Node<E> p = n.getParent();
    if (p == null) throw new BoundaryException("No parent");
    return p;
  } 
 
  public Node<E> getLeft(Node<E> n) {
    Node<E> l = n.getLeft();
    //if (l == null) throw new BoundaryException("No left child");
    // Do not throw any exception, OK to return a null
    return l;
  }

  public Node<E> getRight(Node<E> n) {
    Node<E> r = n.getRight();
    //if (r == null) throw new BoundaryException("No right child");
    // Do not throw any exception, OK to return a null
    return r;
  }

  public Node<E> getSibling(Node<E> n) throws BoundaryException {
    Node<E> p = getParent(n);
    if (p != null) {
      Node<E> s;
      Node<E> l = getLeft(p);
      if (l == n) s = getRight(p);
      else s = l;
      if (s != null) return s;
    }
    throw new BoundaryException("Node has no sibling");
  }
  
  public boolean hasLeft(Node<E> n) throws InvalidNodeException {
    if (n == null) throw new InvalidNodeException("Node is null");
    return (n.getLeft() != null);
  }
  
  public boolean hasRight(Node<E> n) throws InvalidNodeException {
    if (n == null) throw new InvalidNodeException("Node is null");
    return (n.getRight() != null);
  }
   
  public boolean isEmpty() {return (btSize == 0);}
  
  public boolean isRoot(Node<E> n) 
    throws InvalidNodeException, EmptyTreeException {
    if (n == null) throw new InvalidNodeException("Node is null");
    return (n == getRoot());
  }
 
  public boolean isInternal(Node<E> n) throws InvalidNodeException {
    if (n == null) throw new InvalidNodeException("Node is null");
    return (hasLeft(n) || hasRight(n));
  }
  
  public NodeList<E> getChildList(Node<E> n) throws InvalidNodeException {
    NodeList<E> childList = new NodeList<E>(); 
    if (hasLeft(n)) childList.addLast(n.getLeft().getElement());  // Recursive
    if (hasRight(n)) childList.addLast(n.getRight().getElement()); // Recursive
    return childList;
  }
  
  public NodeList<Node<E>> getTreeList() 
  throws InvalidNodeException, EmptyTreeException {
    NodeList<Node<E>> treeList = new NodeList<Node<E>>(); 
    if (btSize != 0) getSubtreeList(getRoot(), treeList);
    return treeList;
  }
  
  public void getSubtreeList(Node<E> n, NodeList<Node<E>> l) 
  throws InvalidNodeException, EmptyTreeException {
    l.addLast(n);
    if (hasLeft(n)) getSubtreeList(n.getLeft(), l); //use Node getLeft
    if (hasRight(n)) getSubtreeList(n.getRight(), l);// or BT getLeft?
  }
  
  public void getSubtreeLevelsList(Node<E> n, NodeList<Integer> l) 
  throws InvalidNodeException, EmptyTreeException {
    l.addLast(n.getLevel());
    if (hasLeft(n)) getSubtreeLevelsList(n.getLeft(), l); 
    if (hasRight(n)) getSubtreeLevelsList(n.getRight(), l);
  }

  public NodeList<DiagramNode<E>> getDiagramList() throws InvalidNodeException,
  EmptyTreeException, EmptyListException, BoundaryException {
    NodeList<DiagramNode<E>> diagramList = new NodeList<DiagramNode<E>>();
    NodeList<Node<E>> treeList = getTreeList();  
    int size = treeList.getNLSize();
    int pos=0, pos1=0, pos2=0, pos3=0, pos4=0, pos5=0, pos6=0;
    boolean levelInBounds = true;

    ListNode<Node<E>> listNode = treeList.getFirst();
    Node<E> node = listNode.getElement();
    E element = node.getElement();
    int level = 1;
    DiagramNode<E> dn = new DiagramNode<E>(element, level, 1);
    diagramList.addLast(dn);
  
    for (int i = 1; i < size; i++) {
      listNode = treeList.getNext(listNode);
      node = listNode.getElement();
      element = node.getElement();
      level = node.getLevel();      
      switch (level) {
        case 1:  pos1++;  pos = pos1;  break;
        case 2:  pos2++;  pos = pos2;  break;
        case 3:  pos3++;  pos = pos3;  break;
        case 4:  pos4++;  pos = pos4;  break;
        case 5:  pos5++;  pos = pos5;  break;
        case 6:  pos6++;  pos = pos6;  break;
        default: levelInBounds = false;  break;
      }
      if (levelInBounds) {        
        dn = new DiagramNode<E>(element, level, pos);
        diagramList.addLast(dn);
      }    
    }// end for    
    return diagramList;    
  } 
  
  public void printTreeDiagram(BinaryTree<E> t) throws 
  InvalidNodeException, EmptyTreeException, EmptyListException {
    int height = this.getBTHeight();
    
    NodeList<Node<E>> list = this.getTreeList();
    ListNode<Node<E>> root = list.getFirst();
    Integer rootEl = (Integer) root.getElement().getElement(); 
    
    for (int i = 0; i < height * 5; i++) {
      System.out.print(" ");
    }
    System.out.println(rootEl);
  }

  /////////////////////////////////////////////////////////////////////
  // NEED TO GENERICIZE THIS METHOD
  public void printDiagram(BinaryTree<E> t) throws 
  InvalidNodeException, EmptyTreeException, EmptyListException {
    System.out.println("This rough diagram must be run on a full screen");
    System.out.println("and is tidiest for trees with only single digit element");
    System.out.println("e.g. as in common usage of holding binary digits 1,0");
    System.out.println("Only shows top 6 levels"); // Consider enabling diagram
    int height = this.getBTHeight();               // for subtrees
    Node<E> root = this.getRoot();
    Node<E> left = root.getLeft();
    Node<E> right = root.getRight();

    Node<E> ll = left.getLeft();
    Node<E> lr = left.getRight();
    Node<E> rl = right.getLeft();
    Node<E> rr = right.getRight();

    Node<E> lll = ll.getLeft();
    Node<E> llr = ll.getRight();
    Node<E> lrl = lr.getLeft();
    Node<E> lrr = lr.getRight();    
    Node<E> rll = rl.getLeft();
    Node<E> rlr = rl.getRight();
    Node<E> rrl = rr.getLeft();
    Node<E> rrr = rr.getRight();

    Node<E> llll = lll.getLeft();
    Node<E> lllr = lll.getRight();
    Node<E> llrl = llr.getLeft();
    Node<E> llrr = llr.getRight();    
    Node<E> lrll = lrl.getLeft();
    Node<E> lrlr = lrl.getRight();
    Node<E> lrrl = lrr.getLeft();
    Node<E> lrrr = lrr.getRight();
    Node<E> rlll = rll.getLeft();
    Node<E> rllr = rll.getRight();
    Node<E> rlrl = rlr.getLeft();
    Node<E> rlrr = rlr.getRight();    
    Node<E> rrll = rrl.getLeft();
    Node<E> rrlr = rrl.getRight();
    Node<E> rrrl = rrr.getLeft();
    Node<E> rrrr = rrr.getRight();
  
    Integer el = (Integer) root.getElement();     
    for (int i = 0; i < 64; i++) {
      System.out.print(" ");
    }
    System.out.println(el);

    this.iterateDiagram(root, 64, 32);
    System.out.println();
    this.iterateDiagram(left, 32, 16);
    System.out.print("              "); // 14 spaces
    this.iterateDiagram(right, 32, 16);
    System.out.println();

    this.iterateDiagram(ll, 16, 8);
    System.out.print("       "); // 7 spaces
    this.iterateDiagram(lr, 16, 8);
    System.out.print("       "); 
    this.iterateDiagram(rl, 16, 8);
    System.out.print("       "); 
    this.iterateDiagram(rr, 16, 8);
    System.out.println();

    this.iterateDiagram(lll, 8, 4);
    System.out.print("   ");        // 3 spaces
    this.iterateDiagram(llr, 8, 4);
    System.out.print("    ");       // 4
    this.iterateDiagram(lrl, 8, 4);
    System.out.print("   ");        // 3 
    this.iterateDiagram(lrr, 8, 4);
    System.out.print("    ");       // 4
    this.iterateDiagram(rll, 8, 4);
    System.out.print("   ");        // 3
    this.iterateDiagram(rlr, 8, 4);
    System.out.print("    ");       // 4
    this.iterateDiagram(rrl, 8, 4);
    System.out.print("   ");        // 3
    this.iterateDiagram(rrr, 8, 4);
    System.out.println();

    this.iterateDiagram(llll, 4, 2);
    System.out.print(" ");        // 1 space 
    this.iterateDiagram(lllr, 4, 2);
    System.out.print("  ");       // 2
    this.iterateDiagram(llrl, 4, 2);
    System.out.print(" ");        // 1 
    this.iterateDiagram(llrr, 4, 2);
    System.out.print("   ");      // 3 
    this.iterateDiagram(lrll, 4, 2);
    System.out.print(" ");        // 1
    this.iterateDiagram(lrlr, 4, 2);
    System.out.print("  ");       // 2 
    this.iterateDiagram(lrrl, 4, 2);
    System.out.print(" ");        // 1
    this.iterateDiagram(lrrr, 4, 2);
    System.out.print("   ");      // 3
    this.iterateDiagram(rlll, 4, 2);
    System.out.print(" ");        // 1
    this.iterateDiagram(rllr, 4, 2);
    System.out.print("  ");       // 2
    this.iterateDiagram(rlrl, 4, 2);
    System.out.print(" ");        // 1  
    this.iterateDiagram(rlrr, 4, 2);
    System.out.print("   ");      // 3
    this.iterateDiagram(rrll, 4, 2);
    System.out.print(" ");        // 1
    this.iterateDiagram(rrlr, 4, 2);
    System.out.print("  ");       // 2 
    this.iterateDiagram(rrrl, 4, 2);
    System.out.print(" ");        // 1
    this.iterateDiagram(rrrr, 4, 2);
    System.out.println();
 
  }

  public void iterateDiagram(Node<E> n, int pos, int offset) 
  throws InvalidNodeException {    
    Node<E> l = null;
    Node<E> r = null;
    Integer lEl = null;
    Integer rEl = null;
    boolean leftFirst = false;
    if (hasLeft(n)) l = n.getLeft(); 
    if (hasRight(n)) r = n.getRight();
    if (l != null) {
      lEl = (Integer) l.getElement();
      for (int i = 0; i < pos - offset; i++) {
        System.out.print(" ");
      }
      System.out.print(lEl);
      leftFirst = true;
    }
    if (r != null) {
      int ofst = offset;
      if (leftFirst) ofst = 2*offset - pos; //If leftFirst then
      rEl = (Integer) r.getElement(); // i < pos + offset -(pos -offset)
      for (int i = 0; i < pos + ofst -2; i++) { // = pos + 2*offset -pos = 2*offset, so
        System.out.print(" ");        // pos + ofst = 2*offset, so
      }                               // ofst = 2*offset - pos
    System.out.print(rEl);  
    }  
  }


  public void setBTHeight(int i) {
    this.btHeight = i;
  }

  public Node<E> setRoot(E e) throws TreeNotEmptyException {
    if (!isEmpty()) throw new TreeNotEmptyException("Tree is not empty");
    btSize = 1;
    btHeight = 1;
    root = new Node<E>(e, null, null, null);
    root.setLevel(1);
    return root;
  }

  public Node<E> setLeft(Node<E> n, E e) throws InvalidNodeException {
    Node<E> test = getLeft(n);
    if (test != null) throw new InvalidNodeException("Node already has left child");
    Node<E> l = new Node<E>(e, n, null, null);
    n.setLeft(l);
    btSize++;
    if (n.getLevel() == btHeight) {
      btHeight++;
      l.setLevel(btHeight);
    } 
    else {l.setLevel(n.getLevel() + 1);}
    return l;
  }

  public Node<E> setRight(Node<E> n, E e) throws InvalidNodeException {
    Node<E> test = getRight(n);
    if (test != null) throw new InvalidNodeException("Node already has left child");
    Node<E> r = new Node<E>(e, n, null, null);
    n.setRight(r);
    btSize++;
    if (n.getLevel() == btHeight) {
      btHeight++;
      r.setLevel(btHeight);
    } 
    else {r.setLevel(n.getLevel() + 1);}
    return r;
  }

  //Remove a node, replace it with its one child, two children causes error
  // and return element stored at the removed node
  //NEED TO SUBTRACT 1 FROM LEVEL FOR EACH SUBNODE OF n AND POSSIBLY
  //DECREMENT BTHEIGHT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  public E removeNode(Node<E> n) throws InvalidNodeException,
  BoundaryException {
    Node<E> l = getLeft(n);
    Node<E> r = getRight(n);
    if (l != null && r != null) 
      throw new InvalidNodeException("Node has two children");
    Node<E> child;
    if (l != null) child = l;
    else if (r != null) child = r;
    else child = null;  // Node to be removed is external
    if (n == root) {  // Node to be removed is the root
      if (child != null) child.setParent(null);
      root = child; 
    } 
    else {  // Node to be removed is not the root
      Node<E> p = n.getParent();
      if (n == p.getLeft()) p.setLeft(child); // N.B set to null if node is external
      else p.setRight(child);  // N.B set to null if node to be removed is external
      if (child != null) child.setParent(p); // Node removed was internal
    }
    btSize--;
    return n.getElement();
  }

  public E replaceElement(Node<E> n, E e) throws InvalidNodeException {
    E oldElement = n.getElement();
    n.setElement(e);
    return oldElement;
  }
 
  public void setDescendentLevels(Node<E> rootSub) throws InvalidNodeException {
    int parentLevel = rootSub.getParent().getLevel();
    rootSub.setLevel(parentLevel + 1);
    if (hasLeft(rootSub)) this.setDescendentLevels(rootSub.getLeft()); // Recursive
    if (hasRight(rootSub)) this.setDescendentLevels(rootSub.getRight());// Recursive
  } 

  // Attach two trees as subtrees of an external node
  // i.e. the roots of these trees become children of the node
  public void attachTrees(Node<E> n, BinaryTree<E> TLeft, BinaryTree<E> TRight) 
  throws InvalidNodeException, EmptyTreeException {
    if (isInternal(n)) throw new InvalidNodeException("Node is not external");
    btSize = btSize + TLeft.getBTSize() + TRight.getBTSize();
    int TLeftHeight = TLeft.getBTHeight();
    int TRightHeight = TRight.getBTHeight();
    int maxSubHeight = (TLeftHeight > TRightHeight) ? TLeftHeight : TRightHeight;
    int oldHeight = this.getBTHeight();
    int attachmentNodeLevel = n.getLevel(); 
    int hOffset = oldHeight - attachmentNodeLevel;
    this.setBTHeight(oldHeight + maxSubHeight - hOffset);
    if (!TLeft.isEmpty()) {
      Node<E> leftTreeRoot = TLeft.getRoot();
      n.setLeft(leftTreeRoot);
      leftTreeRoot.setParent(n);
      leftTreeRoot.setLevel(attachmentNodeLevel);/////////////////////
      setDescendentLevels(leftTreeRoot);
      //augmentSubTreeLevels(leftTreeRoot, attachmentNodeLevel);
    }
    if (!TRight.isEmpty()) {
      Node<E> rightTreeRoot = TRight.getRoot();
      n.setRight(rightTreeRoot);
      rightTreeRoot.setParent(n);
      rightTreeRoot.setLevel(attachmentNodeLevel);/////////////////////
      setDescendentLevels(rightTreeRoot);
      //augmentSubTreeLevels(rightTreeRoot, attachmentNodeLevel);
    }       
  }

   // Print the integer elements stored in each node
  public static void printList(NodeList<Node<Integer>> nl) throws EmptyListException, 
  InvalidNodeException, BoundaryException {
    ListNode<Node<Integer>> nextNode = nl.getFirst();
    if (nl.getNLSize() == 1) { System.out.print( nextNode.getElement().getElement() + " " ); };
    for (int i = 0; i < nl.getNLSize() -1; i++) { // Only operates for size > 1;    
      System.out.print( nextNode.getElement().getElement() + " " );
      nextNode = nl.getNext(nextNode);
    } 
    System.out.print( nextNode.getElement().getElement() + " " );
    System.out.println();
  }

  // Print the level property stored in each node
  public static void printList2(NodeList<Integer> nl) throws EmptyListException, 
  InvalidNodeException, BoundaryException {
    ListNode<Integer> nextNode = nl.getFirst();
    if (nl.getNLSize() == 1) { System.out.print( nextNode.getElement() + " " ); };
    for (int i = 0; i < nl.getNLSize() -1; i++) { // Only operates for size > 1;    
      System.out.print( nextNode.getElement() + " " );
      nextNode = nl.getNext(nextNode);
    } 
    System.out.print( nextNode.getElement() + " ");
    System.out.println();
  }

  public static void printDiagramNodeList(NodeList<DiagramNode<Integer>> nl) 
  throws EmptyListException, InvalidNodeException, BoundaryException {
    ListNode<DiagramNode<Integer>> nextListNode = nl.getFirst();
    DiagramNode<Integer> nextNode = nextListNode.getElement();
    if (nl.getNLSize() == 1) { 
      System.out.print( "El: " + nextNode.getElement() + " " );
      System.out.print( "Lev: " + nextNode.getLevel() + " " );
      System.out.print( "Pos: " + nextNode.getPosition() + " " );
    }
    for (int i = 0; i < nl.getNLSize() -1; i++) { // Only operates for size > 1;    
      System.out.print( "El: " + nextNode.getElement() + " " );
      System.out.print( "Lev: " + nextNode.getLevel() + " " );
      System.out.print( "Pos: " + nextNode.getPosition() + "   " );
      nextListNode = nl.getNext(nextListNode);
      nextNode = nextListNode.getElement();
    }
    System.out.print( "El: " + nextNode.getElement() + " " );
    System.out.print( "Lev: " + nextNode.getLevel() + " " );
    System.out.print( "Pos: " + nextNode.getPosition() + " " );
    System.out.println(); 
  }  

  
  public static void testRoutine1() // A TEST ROUTINE
  throws EmptyTreeException, TreeNotEmptyException,
  InvalidNodeException, BoundaryException, EmptyListException {
    BinaryTree<Integer> bt4 = new BinaryTree<Integer>();
    bt4.setRoot(40);
    Node<Integer> root4 = bt4.getRoot();
    Node<Integer> leftCh4l1 = bt4.setLeft(root4, 41);
    Node<Integer> leftCh4l2 = bt4.setLeft(leftCh4l1, 42);
    Node<Integer> leftCh4l3 = bt4.setLeft(leftCh4l2, 43);
    Integer left4intL2 = leftCh4l2.getElement();
    Integer left4intL3 = leftCh4l3.getElement();
    System.out.println("BT4 left2: " + left4intL2);
    System.out.println("BT4 left3: " + left4intL3);
    System.out.println("BT4 Size: " + bt4.getBTSize());
   
    BinaryTree<Integer> bt5 = new BinaryTree<Integer>();
    BinaryTree<Integer> bt6 = new BinaryTree<Integer>();
    Node<Integer> root5 = bt5.setRoot(10);
    bt5.setLeft(root5, 11);
    bt5.setRight(root5, 12);
    Node<Integer> root6 = bt6.setRoot(20);
    bt6.setLeft(root6, 21);
    bt6.setRight(root6, 22);

    bt4.attachTrees(leftCh4l3, bt5, bt6); // Attach trees
    System.out.println("BT4 Size: " + bt4.getBTSize());
    NodeList<Node<Integer>> nList4 = bt4.getTreeList();
    System.out.print("BT4 list: ");
    printList(nList4); // Print list
    
    Node<Integer> leftLev4 = leftCh4l3.getLeft();
    Integer leftLev4int = leftLev4.getElement();
    System.out.println("BT4 left4: " + leftLev4int);
    Node<Integer> leftLev5 = leftLev4.getLeft();
    Integer leftLev5int = leftLev5.getElement();
    System.out.println("BT5 left5: " + leftLev5int);
    
    NodeList<Node<Integer>> aSubList = new NodeList<Node<Integer>>();
    bt4.getSubtreeList(leftLev4, aSubList );    
    printList(aSubList);
    NodeList<Node<Integer>> aSingleSubList = new NodeList<Node<Integer>>();
    bt4.getSubtreeList(leftLev5, aSingleSubList );
    printList(aSingleSubList);


    Integer replace = 1612;
    bt4.replaceElement(leftLev4, replace);
    NodeList<Node<Integer>> nListReplaced = bt4.getTreeList(); // Get list
    printList(nListReplaced);
    replace = 92000023;
    bt4.replaceElement(leftLev5, replace);
    nListReplaced = bt4.getTreeList(); // Get list
    printList(nListReplaced);
   
    //CANT DELETE AN INTERNAL NODE!!!!!
    bt4.removeNode(leftLev5); // works when do both!!
    bt4.removeNode(leftLev4); // doesn't, when dont do 5 first!
    nListReplaced = bt4.getTreeList(); // Get list
    printList(nListReplaced);

    NodeList<Integer> levelsList = new NodeList<Integer>();
    bt4.getSubtreeLevelsList(bt4.getRoot(), levelsList);
    System.out.println("BT4 Size: " + bt4.getBTSize());
    printList2(levelsList);
  }// end testRoutine1


  // MAIN METHOD- TEST PURPOSES ////////////////////////////////////////
  public static void main(String[] args) 
  throws EmptyTreeException, TreeNotEmptyException,
  InvalidNodeException, BoundaryException, EmptyListException {
   
    BinaryTree<Integer> bt2 = new BinaryTree<Integer>();
    BinaryTree<Integer> bt3 = new BinaryTree<Integer>();
    BinaryTree<Integer> bt4 = new BinaryTree<Integer>();
    BinaryTree<Integer> bt5 = new BinaryTree<Integer>();
    BinaryTree<Integer> bt6 = new BinaryTree<Integer>();
    BinaryTree<Integer> bt7 = new BinaryTree<Integer>();
    BinaryTree<Integer> bt8 = new BinaryTree<Integer>();
    BinaryTree<Integer> bt9 = new BinaryTree<Integer>();
    Node<Integer> root2 = bt2.setRoot(0);
    bt2.setLeft(root2, 1);
    bt2.setRight(root2, 2);
    Node<Integer> root3 = bt3.setRoot(3);
    bt3.setLeft(root3, 4);
    bt3.setRight(root3, 5);
    Node<Integer> root4 = bt4.setRoot(6);
    bt2.setLeft(root4, 7);
    bt2.setRight(root4, 8);
    Node<Integer> root5 = bt5.setRoot(9);
    bt3.setLeft(root5, 0);
    bt3.setRight(root5, 1);
    Node<Integer> root6 = bt6.setRoot(2);
    bt2.setLeft(root6, 3);
    bt2.setRight(root6, 4);
    Node<Integer> root7 = bt7.setRoot(5);
    bt3.setLeft(root7, 6);
    bt3.setRight(root7, 7);
    Node<Integer> root8 = bt8.setRoot(8);
    bt2.setLeft(root8, 9);
    bt2.setRight(root8, 0);
    Node<Integer> root9 = bt9.setRoot(1);
    bt3.setLeft(root9, 2);
    bt3.setRight(root9, 3);
  
    BinaryTree<Integer> bt70 = new BinaryTree<Integer>();
    bt70.setRoot(8);
    Node<Integer> rt = bt70.getRoot();
    Node<Integer> lCh = bt70.setLeft(rt, 7);
    Node<Integer> rCh = bt70.setRight(rt, 6);
    Node<Integer> llCh = bt70.setLeft(lCh, 5);
    Node<Integer> lrCh = bt70.setRight(lCh, 4);
    Node<Integer> rlCh = bt70.setLeft(rCh, 3);
    Node<Integer> rrCh = bt70.setRight(rCh, 2);
    System.out.println();
    
   // NB ATTACHING SAME TREES MORE THAN ONCE CAN CAUSE AN ANOMALY, NAMELY 
   // INVALIDNODEeXCEPTION, TRYING TO ATTACH TREES TO "INTERNAL NODES", IF
   // SUBSEQUENT TREES LATER ATTEMPT ATTACHMENT BELOW
   
    bt70.attachTrees(llCh, bt2, bt3); // Attach trees VALID POINTERS
    bt70.attachTrees(lrCh, bt4, bt5); // Attach trees
    bt70.attachTrees(rlCh, bt6, bt7); // Attach trees
    bt70.attachTrees(rrCh, bt8, bt9); // Attach trees
 
    Node<Integer> lll = llCh.getLeft();
    Node<Integer> llr = llCh.getRight();
    Node<Integer> lrl = lrCh.getLeft();///
    Node<Integer> lrr = lrCh.getRight();
    Node<Integer> rll = rlCh.getLeft();
    Node<Integer> rlr = rlCh.getRight();
    Node<Integer> rrl = rrCh.getLeft();
    Node<Integer> rrr = rrCh.getRight();
   
    Node<Integer> llll = lll.getLeft();
    Node<Integer> lllr = lll.getRight();
    Node<Integer> llrl = llr.getLeft();
    Node<Integer> llrr = llr.getRight();
    Node<Integer> lrll = lrl.getLeft();///
    Node<Integer> lrlr = lrl.getRight();///
    Node<Integer> lrrl = lrr.getLeft();
    Node<Integer> lrrr = lrr.getRight();
    Node<Integer> rlll = rll.getLeft();
    Node<Integer> rllr = rll.getRight();
    Node<Integer> rlrl = rlr.getLeft();
    Node<Integer> rlrr = rlr.getRight();
    Node<Integer> rrll = rrl.getLeft();
    Node<Integer> rrlr = rrl.getRight();
    Node<Integer> rrrl = rrr.getLeft();
    Node<Integer> rrrr = rrr.getRight();    

    Integer i = llll.getElement();
    System.out.println("Element of llll: " + i);
   
    Integer l2lev = llCh.getLevel();
    System.out.println("Level of ll: " + l2lev); 
    Integer l3lev = lll.getLevel();
    System.out.println("Level of lll: " + l3lev);
    Integer l4lev = llll.getLevel();
    System.out.println("Level of llll: " + l4lev); 

    BinaryTree<Integer> bt88 = new BinaryTree<Integer>();
    bt88.setRoot(8);
    Integer lev = bt88.getRoot().getLevel();
    System.out.println("Level of BT88: " + lev);
    BinaryTree<Integer> bt99 = new BinaryTree<Integer>();
    bt99.setRoot(9);
    System.out.println("       FINAL ATTACH ");
    bt70.attachTrees(llll, bt88, bt99); // attach trees
    bt70.attachTrees(lllr, bt88, bt99); // attach trees
    bt70.attachTrees(llrl, bt88, bt99); // attach trees
    bt70.attachTrees(llrr, bt88, bt99); // attach trees
    bt70.attachTrees(lrll, bt88, bt99); // attach trees//
    bt70.attachTrees(lrlr, bt88, bt99); // attach trees
    bt70.attachTrees(lrrl, bt88, bt99); // attach trees
    bt70.attachTrees(lrrr, bt88, bt99); // attach trees
    bt70.attachTrees(rlll, bt88, bt99); // attach trees
    bt70.attachTrees(rllr, bt88, bt99); // attach trees
    bt70.attachTrees(rlrl, bt88, bt99); // attach trees
    bt70.attachTrees(rlrr, bt88, bt99); // attach trees
    bt70.attachTrees(rrll, bt88, bt99); // attach trees
    bt70.attachTrees(rrlr, bt88, bt99); // attach trees
    bt70.attachTrees(rrrl, bt88, bt99); // attach trees
    bt70.attachTrees(rrrr, bt88, bt99); // attach trees
    
    bt70.printDiagram(bt70);
    System.out.println();
    
    Node<Integer> lllll = llll.getRight();
    Integer l5El = lllll.getLevel();
    System.out.println("lllll level: " + l5El); 

    int h70 =  bt70.getBTHeight();
    System.out.println("BT70 Height: " + h70);
      
    NodeList<Integer> levelsList70 = new NodeList<Integer>();
    bt70.getSubtreeLevelsList(bt70.getRoot(), levelsList70);
    System.out.print("BT70 Levels Augmented: ");
    printList2(levelsList70);
    
    NodeList<Node<Integer>> nListA = bt70.getTreeList(); // Get list
    System.out.print("BT70 list: ");
    printList(nListA); // Print list

    NodeList<DiagramNode<Integer>> dn = bt70.getDiagramList();
    int sz = dn.getNLSize();
    System.out.println("DN Size: " + sz); //Should be 63 for full 6 levels
    printDiagramNodeList(dn);

  }// End main


 static class DiagramNode<E> { // Node type used only for tree diagram
    private E element;
    private int level;    // Root is level 1
    private int position; // Define node's position on its level, left to right
    public DiagramNode(E e, int l, int p) {
      setElement(e);
      setLevel(l);
      setPosition(p);   
    }
    public E getElement() {return element;}       
    public int getLevel() {return level;}
    public int getPosition() {return position;}
    public void setElement(E e) {element = e;}
    public void setLevel(int i) {level = i;}
    public void setPosition(int p) {position = p;}
  }// End internal class DiagramNode<E>


  static class Node<E> {
    private E element;
    private int level;
    private Node<E> parent, left, right;   
    public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
      setElement(e);
      setParent(p);
      setLeft(l);    
      setRight(r); 
    }
    public E getElement() {return element;}       
    public Node<E> getParent() {return parent;}
    public Node<E> getLeft() {return left;}
    public Node<E> getRight() {return right;}
    public int getLevel(){return level;}
    public void setParent(Node<E> p) {parent = p;}
    public void setLeft(Node<E> l) {left = l;}
    public void setRight(Node<E> r) {right = r;}
    public void setElement(E e) {element = e;}
    public void setLevel(int i) {level = i;}
  }// End internal class Node<E>
  

  static class ListNode<E> {
    private E element;
    private ListNode<E> previous, next;
    public ListNode(E e, ListNode<E> p, ListNode<E> n) {
      element = e;
      previous = p;
      next = n;
    }
    public E getElement() throws InvalidNodeException {
      if ((previous == null) && (next == null)) 
        throw new InvalidNodeException("Invalid node");
      return element;
    }
    public ListNode<E> getPrevious() {return previous;}
    public ListNode<E> getNext() {return next;}  
    public void setPrevious(ListNode<E> p) {previous = p;}
    public void setNext(ListNode<E> l) {next = l;}
    public void setElement(E e) {element = e;}  
  }// End internal class ListNode<E>


  static class NodeList<E> {
    private int nlSize;
    private ListNode<E> head, tail;
    
    public NodeList() {
      nlSize = 0;
      head = new ListNode<E>(null, null, null);
      tail = new ListNode<E>(null, head, null);
      head.setNext(tail);
    }
    public int getNLSize() {return nlSize;}

    public ListNode<E> getFirst() throws EmptyListException {
      if (isEmpty()) throw new EmptyListException("Empty list");
      return head.getNext();
    }
    
    public ListNode<E> getLast() throws EmptyListException {
      if (isEmpty()) throw new EmptyListException("Empty list");
      return tail.getPrevious();
    }
    
    public ListNode<E> getPrevious(ListNode<E> n) throws BoundaryException {
      ListNode<E> prv = n.getPrevious(); 
      if (prv == head) 
        throw new BoundaryException("attempt to access before start of list");
      return prv;
    }

    public ListNode<E> getNext(ListNode<E> n) throws BoundaryException {
      ListNode<E> nxt = n.getNext(); 
      if (nxt == tail) 
        throw new BoundaryException("attempt to access after end of list");
      return nxt;
    }
    
    public void addFirst(E e) {
      nlSize ++;
      ListNode<E> n = new ListNode<E>(e, head, head.getNext());
      head.getNext().setPrevious(n);
      head.setNext(n);
    }
    
    public void addLast(E e) {
      nlSize ++;
      ListNode<E> n = new ListNode<E>(e, tail.getPrevious(), tail);
      tail.getPrevious().setNext(n);
      tail.setPrevious(n);      
    }
    
    //more to do

    public boolean isEmpty() {return (nlSize == 0);}

  }// End internal class NodeList<E>

}// End class BinaryTree


// Must keep Exception classes external: a generic class cannot 
// extend java.lang.Throwable

class InvalidNodeException extends Exception {
  public InvalidNodeException(String s){
    System.out.println(s);
  }
}

class EmptyTreeException extends Exception {
  public EmptyTreeException(String s){
    System.out.println(s);
  }
}

class EmptyListException extends Exception {
  public EmptyListException(String s){
    System.out.println(s);
  }
}

class BoundaryException extends Exception {
  public BoundaryException(String s){
    System.out.println(s);
  }
}

class TreeNotEmptyException extends Exception {
  public TreeNotEmptyException(String s){
    System.out.println(s);
  }
}
