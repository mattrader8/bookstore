import { ShoppingCartItem } from "./ShoppingCartItem.js";

class ShoppingCart {
  constructor() {
    this._items = []; /* Array of Shopping Cart Item */
  }

  get numberOfItems() {
    return this._items.reduce((count, item) => {
      return count + item.quantity;
    }, 0);
  }

  get empty() {
    return this._items.length <= 0;
  }

  get subtotal() {
    return this._items.reduce((amount, item) => {
      return amount + item.book.price * item.quantity;
    }, 0);
  }

  get total() {
    return this.subtotal + this.surcharge;
  }

  get surcharge() {
    return 500;
  }

  get items() {
    return ShoppingCart.frozenArray(this._items);
  }

  clear() {
    this._items = [];
  }

  addItem(book /*: Object */, quantity = 1) {
    let existingItem = this._items.find(
      (item) => item.book.bookId == book.bookId
    );
    if (!existingItem) {
      let newItem = new ShoppingCartItem(book);
      newItem.quantity = quantity;
      this._items.push(newItem);
    } else {
      existingItem.quantity++;
    }
  }

  update(book /*: Object*/, quantity) {
    if (quantity < 0 || quantity > 99) return;

    let existingItemIndex = this._items.findIndex(
      (item) => item.book.bookId == book.bookId
    );
    if (existingItemIndex !== -1) {
      if (quantity !== 0) {
        this._items[existingItemIndex].quantity = quantity;
      } else {
        // remove item if quantity == 0
        this._items.splice(existingItemIndex, 1);
      }
    }
  }

  toJSON() {
    return {
      items: ShoppingCart.frozenArray(this._items),
    };
  }

  static frozenArray(inArray) {
    let tempArr = [];
    Object.keys(inArray).forEach((element) => {
      tempArr.push(inArray[element]);
    });
    return Object.freeze(tempArr);
  }
}

export { ShoppingCart };
