    def remove(self, value):
        if not self.root:
            return False
        elif value == self.root.data:
            node = self.root
            if node.left and node.right: # case 被删除节点左右孩子都存在
                min_node = node.right
                while min_node.left:
                    min_node = min_node.left
                node.data = min_node.data
                BinarySearchTree.__remove__(node.right, node, node.data)
            else:                       # case 最多只有一个孩子存在
                if node.left:
                    self.root = node.left
                else:
                    self.root = node.right
            return True
        elif value < self.root.data and self.root.left:
            return BinarySearchTree.__remove__(self.root.left, self.root, value)
        elif value > self.root.data and self.root.right:
            return BinarySearchTree.__remove__(self.root.right, self.root, value)
        return False

    @staticmethod
    def __remove__(node, parent_node, value):
        if not node or not parent_node:
            return False
        if value == node.data:
            if node.left and node.right: # case 被删除节点左右孩子都存在
                min_node = node.right
                while min_node.left:
                    min_node = min_node.left
                node.data = min_node.data
                BinarySearchTree.__remove__(node.right, node, node.data)
            else:                        # case 最多只有一个孩子存在
                if node == parent_node.left:
                    if node.left:
                        parent_node.left = node.left
                    else:
                        parent_node.left = node.right
                else:
                    if node.left:
                        parent_node.right = node.left
                    else:
                        parent_node.right = node.right
        elif value < node.data and node.left:
            return BinarySearchTree.__remove__(node.left, node, value)
        elif value > node.data and node.right:
            return BinarySearchTree.__remove__(node.right, node, value)
        return False