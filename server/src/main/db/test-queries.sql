#get title based on category name

SELECT title
FROM book JOIN category
WHERE book.category_id = category.category_id
    && name = 'Science Fiction';