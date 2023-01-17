# meta-air-quality
## Opis
...
## Wymagania
| Typ | Minimum | Rekomendowane |
| ------ | ------ | ------ |
| Wolne miejsce | 60 GB | 110 GB |
| RAM | 4 GB | 16GB |

Dodatkowe wymagania systemu oraz zainstalowanyh pakietów: [Wymagania](https://docs.yoctoproject.org/ref-manual/system-requirements.html)
Udało mi się skompilować na VirtualBoxie, system Linux Mint 21, 4 GB RAM + 4 GB SWAP ale bardzo długo trwało + kilka razy się zawiesił system.
Na szczęście można było kontynuować kompilację po restarcie po ewentualnym wyczyszczeniu paczki na której się zacieło.
Aby zmniejszyć zużycie pamięci można zmniejszyć liczbę wykonywanych zadań jednocześnie(np. kompilacja paczki + ściąganie innej) oraz ile wątków może każde zadanie używać.
Plik ```meta-air-quality/kas/threads.yml``` jest tworzony automatycznie po pierwszym ```make build[rpi]``` albo ```make checkout[rpi]```
W nim są zakomentowane 2 opcje:
```yml
BB_NUMBER_THREADS = "1"
PARALLEL_MAKE = "-j 1"
```
BB_THREADS odpowiada za liczbę jednoczesnych zadań.
PARALLEL_MAKE za to ile wątków może jedno zadanie wykorzystywać.

## Setup
Dane komendy wykonać w folderze przeznaczonym do yocto.
```sh
sudo pip3 install kas
git clone https://github.com/Mini-air-quality-station/meta-air-quality
cp meta-air-quality/scripts/* .
make checkout
```

Po tym zabiegu kas powinien ściągnąć wszystkie potrzebne repozytoria oraz stworzyć domyślne pliki.
W pliku ```meta-air-quality/kas/machine.yml``` znajduje się typ raspberry jaki posiadacie. Jeżeli będziecie kompilować tylko na qemu to nie trzeba zmieniać.
Za każdym razem podczas używania nowego terminala i chęci użycia komend bitbake*
bądź celów make które korzystają z bitbake trzeba wykonać komendę:

```sh
source init.sh
```

## Obsługa
Cele make:
| Cel | Opis |
| ------ | ------ |
| build | tworzy obraz na maszynę qemu |
| buildrpi | tworzy obraz na raspberrypi (kas-rpi.yml) |
| clean | czyści pliki obrazu(konf, kompilacji, wynikowe), paczki wchodzące w skład pozostają bez zmiany(kernel, aplikacje, itp) |
| cleankernel | czyści pliki kernela |
| cleanall | clean + cleankernel |
| run | uruchamia stworzony obraz na qemu |
| checkout | aktualizuje pliki konfiguracyjne dla kas-qemu.yml w folderze build/ i ściąga potrzebne repozytoria |
| checkoutrpi | to samo ale dla kas-rpi.yml |
| depends | tworzy pliki task-depends.dot i pn-buildlist |
| recipes | zwraca aktualnie dostępne paczki które można dodać |
| manifest | zwraca plik manifest dla obrazu qemu(końcowe paczki które znajdują się w obrazie) |
| manifestrpi | to samo ale dla raspberrypi |

clean, cleankernel, cleanall, depends i recipes działa na aktualnie "checkoutowanym" buildzie.
Czyli jeżeli ostatnio był wykonywany checkoutrpi bądź buildrpi to te komendy będą działać na plikach tworzonych dla rpi (np. ```make clean``` wyczyści pliki wynikowe dla raspberrypi).

Po zmianach w plikach meta-air-quality/  trzeba wykonać ```make checkout[rpi]``` bądź ```make build[rpi]``` aby komendy używające bitbake zauważyły zmiany
Czasem zmiany w plikach mogą zostać "niezauważone" i bitbake nie zauważy że paczka jest nieaktualna i trzeba ją rekompilować.
W takim wypadku trzeba użyć albo ```make clean``` bądź ```bitbake <recipe> -c clean``` z nazwą paczki która powinna zostać wyczyszczona i skompilowana ponownie przy następnym ```make build```

# Folder z obrazem
Skompilowane pliki(kernel, rootfs...) znajdują się w ```build/tmp/deploy/images/<MACHINE>/```
<MACHINE> to maszyna na którą był tworzony obraz np. 'qemuarm' lub 'raspberrypi'

# 
