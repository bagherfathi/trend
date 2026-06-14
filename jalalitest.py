tmp_text ="۲۴ شهریور ۱۴۰۳"
tmp_text2="۲۴ شهریور ۱۴۰۳، ۱۹:۳۱"
from parsivar import Normalizer


my_normalizer = Normalizer(date_normalizing_needed=True)
print(my_normalizer.normalize(tmp_text))
print(my_normalizer.normalize(tmp_text2))