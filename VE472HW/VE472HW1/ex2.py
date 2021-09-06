import bz2
import os
import sys

BASE_DIR = '/Users/wujiayao/Downloads/tmp/flight_data'


def generate_a_flight(_titles, _flight_raw):
    _flight = {}
    _flight_raw_split = _flight_raw.split(',')
    for _index, _title in enumerate(_titles):
        try:
            _flight[_title] = int(_flight_raw_split[_index])
        except IndexError:
            # end of file or something unexpected
            return None
        except ValueError:
            _flight[_title] = _flight_raw_split[_index]
    return _flight


if __name__ == '__main__':
    # delay
    delay_count_with_carrier = {}
    # late origin
    late_origin_with_weather = {}
    # longest
    longest_delay_with_carrier = {}
    for bz2_file in os.listdir(BASE_DIR):
        if bz2_file.startswith('._') or not bz2_file.endswith('.bz2'):
            continue
        print('Processing {}...'.format(bz2_file), file=sys.stderr)
        with bz2.open(os.path.join(BASE_DIR, bz2_file)) as file:
            content = str(file.read()).split('\\n')
            titles = content[0].split(',')
            for content in content[1:]:
                flight = generate_a_flight(titles, content)
                if flight is not None:
                    try:
                        flight_count = delay_count_with_carrier.get(flight['UniqueCarrier'], 0)
                        if type(flight['DepDelay']) == int and flight['DepDelay'] > 0:
                            flight_count += 1
                        delay_count_with_carrier[flight['UniqueCarrier']] = flight_count
                    except TypeError:
                        pass

                    try:
                        origin_count = late_origin_with_weather.get(flight['Origin'], 0)
                        if type(flight['WeatherDelay']) == int and flight['WeatherDelay'] > 0:
                            origin_count += 1
                        late_origin_with_weather[flight['Origin']] = origin_count
                    except TypeError:
                        pass

                    try:
                        flight_longest_delay = longest_delay_with_carrier.get(flight['UniqueCarrier'], 0)
                        if type(flight['DepDelay']) == int and flight['DepDelay'] > flight_longest_delay:
                            longest_delay_with_carrier[flight['UniqueCarrier']] = flight['DepDelay']
                    except TypeError:
                        pass

    print('2.1====================================')
    print(delay_count_with_carrier)
    max_key_one = max(delay_count_with_carrier, key=delay_count_with_carrier.get)
    print(max_key_one, delay_count_with_carrier[max_key_one])
    print('2.2====================================')
    max_three = sorted(late_origin_with_weather, key=late_origin_with_weather.get, reverse=True)[:3]
    for max_three_each in max_three:
        print(max_three_each, late_origin_with_weather[max_three_each])
    print('2.3====================================')
    print(longest_delay_with_carrier)
