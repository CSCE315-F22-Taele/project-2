
import random
import string
import csv

from random import randrange


def make_line(customer_name , base, chips, brown, cookie, oz16, oz22, n, cnt):
    rows = []
    for i in range(n): #Make n rows
        order_price = 0.0
        row = []
        row.append(customer_name)
        if base > 0:
            base -= 1
            base = randrange(2)
            if base == 0:
                row.append("Burrito")
            else:
                row.append("Bowl")
            protein = randrange(4)
            if protein == 0:  # Beef
                order_price += 8.19
                row.append("Beef")
            elif protein == 1:  # Chicken
                order_price += 7.29
                row.append("Chicken")
            elif protein == 2:  # Steak
                order_price += 8.29
                row.append("Steak")
            elif protein == 3:  # Vegetables
                order_price += 7.29
                row.append("Vegetables")
            queso = bool(random.getrandbits(1))
            guacamole = bool(random.getrandbits(1))
            if guacamole:
                order_price += 2.0
                row.append(1)
            else:
                row.append(0)
            if queso:
                order_price += 2.0
                row.append(1)
            else:
                row.append(0)
        else:
            row.append("N/A")
            row.append("N/A")
            row.append(0)
            row.append(0)
        if chips > 0:
            chips -= 1
            chips_type = randrange(2)
            if chips_type == 0:  # Salsa
                order_price += 2.19
                row.append(1)
            else:
                row.append(0)

            if chips_type == 1:  # Guac
                order_price += 3.69
                row.append(1)
            else:
                row.append(0)

            if chips_type == 2:  # Queso
                order_price += 3.64
                row.append(1)
            else:
                row.append(0)
        else:
            row.append(0)
            row.append(0)
            row.append(0)

        if brown > 0:
            brown -= 1
            order_price += 1.99
            row.append(1)
        else:
            row.append(0)

        if cookie > 0:
            cookie -= 1
            order_price += 1.99
            row.append(1)
        else:
            row.append(0)

        if oz16 > 0:
            oz16 -= 1
            order_price += 1.99
            row.append(1)
        else:
            row.append(0)

        if oz22 > 0:
            oz22 -= 1
            order_price += 1.99
            row.append(1)
        else:
            row.append(0)

        order_price = round(order_price, 2)
        row.append(order_price)
        row.append("10/" + str(cnt) + "/2022" )
        rows.append(row)

    return rows




def main():
    header = ['customer_name', 'base', 'protein', 'guac', 'queso', 'chips_salsa', 'chips_queso', 'chips_guacamole', 'brownie', 'cookie', '16oz', '22oz', 'order_cost', 'date']
    cnt = 1
    check = 0
    with open('OrderEntries.csv', 'w', newline="") as f:
        writer = csv.writer(f)
        writer.writerow(header)
        for i in range(0, 3000):
            #TODO: Customer Name
            letters = string.ascii_lowercase
            Customer_name = ''.join(random.choice(letters) for i in range(7))

            L = [Customer_name]

            # TODO: BASE
            base = randrange(5)
            base_extra = randrange(10)

            #TODO: Date stuff
            if cnt == 8 or cnt == 16:
                if randrange(450) == 3:
                    if cnt <= 21:
                        cnt+=1
            else:
                if randrange(150) == 3:
                    if cnt <= 21:
                        cnt+=1

            # TODO: protein
            if base == 1 or base == 2 or base == 3 or base == 4:
                protein = randrange(4)
                queso = bool(random.getrandbits(1))
                guacamole = bool(random.getrandbits(1))

            # TODO: Chips
            chips = bool(random.getrandbits(1))
            chips_extra = randrange(10)
            if chips:
                chips_type = randrange(3)

            # TODO: Drink
            oz16 = bool(random.getrandbits(1))
            oz16_extra = randrange(10)
            oz22 = bool(random.getrandbits(1))
            oz22_extra = randrange(10)
            # TODO: Dessert
            brownie = bool(random.getrandbits(1))
            brownie_extra = randrange(10)
            cookie = bool(random.getrandbits(1))
            cookie_extra = randrange(10)

            # TODO: Compute total price
            order_price = 0.0

            if base == 1 or base == 3:
                L.append("Burrito")
            elif base == 2 or base == 4:
                L.append("Bowl")
            elif base == 0:
                L.append("N/A")
            #Protein
            if base == 1 or base == 2 or base == 3 or base == 4:
                if protein == 0: # Beef
                    order_price += 8.19
                    L.append("Beef")
                elif protein == 1: # Chicken
                    order_price += 7.29
                    L.append("Chicken")
                elif protein == 2: # Steak
                    order_price += 8.29
                    L.append("Steak")
                elif protein == 3: # Vegetables
                    order_price += 7.29
                    L.append("Vegetables")
            else:
                L.append("N/A")
            #Queso or
            if base == 1 or base == 2 or base == 3 or base == 4:
                if guacamole:
                    order_price += 2.0
                    L.append(1)
                else:
                    L.append(0)
                if queso:
                    order_price += 2.0
                    L.append(1)
                else:
                    L.append(0)
            else:
                L.append(0)
                L.append(0)

            # chips
            if chips:
                if chips_type == 0: # Salsa
                    order_price += 2.19
                    L.append(1)
                else :
                    L.append(0)

                if chips_type == 1: # Guac
                    order_price += 3.69
                    L.append(1)
                else:
                    L.append(0)

                if chips_type == 2: # Queso
                    order_price += 3.64
                    L.append(1)
                else:
                    L.append(0)
            else:
                L.append(0)
                L.append(0)
                L.append(0)

            # Dessert
            if brownie:
                order_price += 1.99
                L.append(1)
            else:
                L.append(0)

            if cookie:
                order_price += 1.99
                L.append(1)
            else:
                L.append(0)

            #Drinks
            if oz16:
                order_price += 2.25
                L.append(1)
            else:
                L.append(0)
            if oz22:
                order_price += 2.45
                L.append(1)
            else:
                L.append(0)

            if order_price == 0.0:
                L[1] = "Buritto"
                L[2] = "Steak"
                order_price+= 8.29
            order_price = round(order_price, 2)
            L.append(order_price)
            check += order_price
            L.append("10/" + str(cnt) + "/2022" )


            #TODO: ADD to file
            writer.writerow(L)
            if base_extra > 3:
                base_extra = 0
            if chips_extra > 3:
                chips_extra = 0
            if brownie_extra > 3:
                brownie_extra = 0
            if cookie_extra > 3:
                cookie_extra = 0
            if oz16_extra > 3:
                oz16_extra = 0
            if oz22_extra > 3:
                oz22_extra = 0

            n = max([base_extra, chips_extra, brownie_extra, cookie_extra, oz16_extra, oz22_extra])
            if (n != 0):
                rows = make_line(Customer_name, base_extra, chips_extra, brownie_extra, cookie_extra, oz16_extra,oz22_extra, n, cnt)
                for row in rows:
                    writer.writerow(row)
                    check += row[12]

            if cnt == 7:
                print("day 7 total cost: ", check)
            if cnt == 14:
                print("day 14 total cost: ", check)
            if cnt == 21:
                print("day 21 total cost: ", check)

    print("Price for 3000 customers: ", check)
if __name__ == "__main__":
    main()