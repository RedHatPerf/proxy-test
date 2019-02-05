# Proxy Test

This is a basic testsuite to benchmark analyze performance of proxies, [Envoy](https://www.envoyproxy.io) in particular.

## Running the test

The harness uses Ansible for provisioning. Most configurable settings are in the `hosts` file in root directory.

In order to start load driver ([Hyperfoil](https://github.com/RedHatPerf/Hyperfoil/)), proxy (Envoy) and backend (simple Vert.x HTTP server) run:

```bash
ansible-playbook -i hosts setup.yml
```

This does not trigger the load yet; benchmark is deployed and started with

```bash
ansible-playbook -i hosts --ask-become-pass test.yml
```

This requires elevated priviledges since we're running system-wide `perf record` on the machine running proxy. The result flamegraph will be stored in `{{ test_flamegraph }}` (defaults to `/tmp/perf.svg`, configure in `hosts`). Performance results are stored in `{{ hyperfoil_dir }}/workspace/run/XXXX/stats` (`{{ hyperfoil_dir }}` defaults to `/tmp/driver`).

Top stop all the services run:

```bash
ansible-playbook -i hosts setup.yml --tags shutdown
```