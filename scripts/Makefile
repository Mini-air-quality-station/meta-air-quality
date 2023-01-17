.PHONY: build recipes manifest
error:
	@echo "Choose option:\nmake {build | buildrpi | clean | cleankernel | cleanall | run | checkout | checkoutrpi | depends | recipes | manifest | manifestrpi}"
build: create_defaults
	kas build meta-air-quality/kas-qemu.yml
buildrpi: create_defaults
	kas build meta-air-quality/kas-rpi.yml
clean:
	bitbake -c clean air-quality-debug
cleankernel:
	bitbake -c clean virtual/kernel
cleanall:
	bitbake -c clean air-quality-debug virtual/kernel
run:
	runqemu nographic qemuparams="-m 1024M"
checkout: create_defaults
	kas checkout meta-air-quality/kas-qemu.yml
checkoutrpi: create_defaults
	kas checkout meta-air-quality/kas-rpi.yml
depends:
	bitbake -g air-quality-debug
recipes:
	bitbake-layers show-recipes
manifest:
	cat build/tmp/deploy/images/qemuarm/air-quality-debug-qemuarm.manifest
manifestrpi: load_variable
	cat build/tmp/deploy/images/${RPI_MACHINE}/air-quality-debug-${RPI_MACHINE}.manifest


# for internal use
load_variable: create_defaults
	$(eval RPI_MACHINE=$(shell cat meta-air-quality/kas/machine.yml | sed -n '4 p' | tr -s ' ' | awk '{$$1=$$1};1' | cut -d' ' -f2))
create_defaults: meta-air-quality/kas/machine.yml meta-air-quality/kas/threads.yml
meta-air-quality/kas/machine.yml:
	printf "header:\n  version: 11\n\nmachine: raspberrypi3" > meta-air-quality/kas/machine.yml
meta-air-quality/kas/threads.yml:
	printf "header:\n  version: 11\n\nlocal_conf_header:\n  #threads: |\n  #  BB_NUMBER_THREADS = \"1\"\n  #  PARALLEL_MAKE = \"-j 3\"" > meta-air-quality/kas/threads.yml


